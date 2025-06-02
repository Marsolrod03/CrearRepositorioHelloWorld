package com.example.data

import com.example.data.data_source.DatabaseDataSource
import com.example.data.data_source.SeriesNetworkDataSource
import com.example.domain.AppError
import com.example.domain.SeriesWrapper
import com.example.domain.model.SerieModel
import com.example.domain.repository.SeriesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import java.util.Calendar
import com.example.data.toSeriesModel


class SeriesRepositoryImpl @Inject constructor(
    private val networkDataSource: SeriesNetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
) : SeriesRepository {

    private var listChange: MutableList<SerieModel> = mutableListOf()
    private var currentPage = 0

    override fun getPagedSeriesFromApi(currentPage: Int): Flow<Result<SeriesWrapper>> = flow {
        try {
            var totalPages = Int.MAX_VALUE
            val pagedResult = networkDataSource.fetchSeries(currentPage)
            pagedResult?.let {
                val seriesList = pagedResult.results.map { series -> series.toSeriesModel() }
                listChange.addAll(seriesList)
                totalPages = pagedResult.total_pages
                val hashMorePages = pagedResult.page < pagedResult.total_pages
                val seriesWrapper = SeriesWrapper(hashMorePages, listChange, totalPages)
                emit(Result.success(seriesWrapper))
            } ?: run {
                emit(Result.success(SeriesWrapper(false, emptyList(), totalPages)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            val error = if (e is IOException) AppError.NoInternet else AppError.UnknownError()
            emit(Result.failure(Exception(error.message)))
        }

    }

    override fun getSeriesDetails(seriesId: String): Flow<Result<SerieModel>> = flow {
        try {
            val serieModel = networkDataSource.fetchDetails(seriesId)
            serieModel?.let {
                emit(Result.success(it))
            } ?: run {
                emit(Result.failure(Exception("Serie no encontrada")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override suspend fun manageSeriesDetails(seriesId: String): Flow<Result<SerieModel>> = flow {
        val localSerie = getSerieById(seriesId.toInt())
        getSeriesDetails(seriesId)
            .collect { result ->
                result.onSuccess { serie ->
                    emit(Result.success(serie))
                }
                result.onFailure {
                    if (localSerie != null) {
                        emit(Result.success(localSerie))
                    } else {
                        emit(Result.failure(it))
                    }
                }
            }
    }

    override suspend fun manageSeriesPagination(): Flow<Result<SeriesWrapper>> = flow {
        val lastPage = getPaginationSeries()
        val seriesFromDatabase = getAllSeriesFromDatabase()
        val totalPages = getTotalPages()
        if (lastPage > currentPage) {
            currentPage = lastPage
            val hashMorePages = currentPage < getTotalPages()
            emit(Result.success(SeriesWrapper(hashMorePages, seriesFromDatabase,totalPages)))
        } else {
            val pageToLoad = currentPage + 1
            getPagedSeriesFromApi(pageToLoad)
                .collect { result ->
                    result.onSuccess { seriesWrapper ->
                        insertSeries(seriesWrapper.listSeries)
                        currentPage ++
                        if(currentPage > lastPage){
                            updatePaginationSeries(currentPage)
                        }
                        emit(Result.success(seriesWrapper))
                    }
                    result.onFailure { throwable ->
                        emit(Result.success(SeriesWrapper(false, seriesFromDatabase, totalPages)))
                        emit(Result.failure(throwable))
                    }
                }
        }
    }

    override suspend fun refreshData() {
        val lastMonday8Am = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        val databaseReset: Long = getLastDatabaseDeletion()
        val now = Calendar.getInstance().timeInMillis

        if(databaseReset < lastMonday8Am){
            clearSeries()
            clearPaginationSeries()
            updateLastDatabaseDeletion(now)
        }
    }

    override suspend fun getAllSeriesFromDatabase(): List<SerieModel> {
        return databaseDataSource.getSeries().map { it.toSeriesModel() }
    }

    override suspend fun getSerieById(id: Int): SerieModel {
        return databaseDataSource.getSerieById(id)
    }

    override suspend fun clearSeries() {
        databaseDataSource.clearSeries()
    }


    override suspend fun insertSeries(series: List<SerieModel>) {
        databaseDataSource.insertSeries(series.map { model -> model.toSeriesEntity() })
    }

    override suspend fun getPaginationSeries(): Int {
        return databaseDataSource.getPaginationSeries()
    }

    override suspend fun insertPaginationSeries(lastLoadedPage: Int) {
        databaseDataSource.insertPagination(lastLoadedPage)
    }

    override suspend fun clearPaginationSeries() {
        databaseDataSource.clearPaginationSeries()
    }

    override suspend fun updatePaginationSeries(newPage: Int) {
        databaseDataSource.updatePaginationSeries(newPage)
    }

    override suspend fun getLastDatabaseDeletion(): Long {
        return databaseDataSource.getLastDatabaseDeletion()
    }

    override suspend fun updateLastDatabaseDeletion(newLastDatabaseDeletion: Long) {
        databaseDataSource.updateLastDatabaseDeletion(newLastDatabaseDeletion)
    }

    override suspend fun getTotalPages(): Int {
        return databaseDataSource.getTotalPages()
    }

    override suspend fun updateTotalPages(newTotalPages: Int) {
        databaseDataSource.updateTotalPages(newTotalPages)
    }


}

