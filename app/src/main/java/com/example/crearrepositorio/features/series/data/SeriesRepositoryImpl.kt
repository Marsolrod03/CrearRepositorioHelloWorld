package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.data_source.DatabaseDataSource
import com.example.crearrepositorio.features.series.data.data_source.SeriesNetworkDataSource
import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity
import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.model.SerieModel
import com.example.crearrepositorio.features.series.domain.repository.SeriesRepository
import com.example.crearrepositorio.features.series.domain.SeriesWrapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException


class SeriesRepositoryImpl @Inject constructor(
    private val networkDataSource: SeriesNetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
) : SeriesRepository {

    private var listChange: MutableList<SerieModel> = mutableListOf()
    private var currentPage = 1

    override fun getPagedSeriesFromApi(currentPage: Int): Flow<Result<SeriesWrapper>> = flow {
        try {
            val pagedResult = networkDataSource.fetchSeries(currentPage)
            pagedResult?.let {
                val seriesList = pagedResult.results.map { series -> series.toSeriesModel() }
                listChange.addAll(seriesList)
                val hashMorePages = pagedResult.page < pagedResult.total_pages
                val seriesWrapper = SeriesWrapper(hashMorePages, listChange)
                emit(Result.success(seriesWrapper))
            } ?: run {
                emit(Result.success(SeriesWrapper(false, emptyList())))
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
            .collect{ result ->
                result.onSuccess { serie ->
                    emit(Result.success(serie))
                }
                result.onFailure {
                    if (localSerie != null) {
                        emit(Result.success(localSerie.toSeriesModel()))
                    }else {
                        emit(Result.failure(it))
                    }
                }
            }
    }

    override suspend fun manageSeriesPagination(): Flow<Result<SeriesWrapper>> = flow {
        if (currentPage == 1) {
            val seriesFromDatabase = getAllSeriesFromDatabase()
            if (seriesFromDatabase.isNotEmpty()) {
                emit(Result.success(SeriesWrapper(true, seriesFromDatabase)))
            }
        }
        getPagedSeriesFromApi(currentPage)
            .collect { result ->
                result.onSuccess { seriesWrapper ->
                    if (currentPage == 1) {
                        insertSeries(seriesWrapper.listSeries)
                        updatePaginationSeries(currentPage)
                    }
                    emit(Result.success(seriesWrapper))

                }
                result.onFailure { error ->
                    emit(Result.failure(error))
                }

            }
    }

    override suspend fun refreshData() {
        clearPaginationSeries()
        insertPaginationSeries(1)
        insertSeries(emptyList())
    }

    override suspend fun getAllSeriesFromDatabase(): List<SerieModel> {
        return databaseDataSource.getSeries().map { it.toSeriesModel() }
    }

    override suspend fun getSerieById(id: Int): SeriesEntity? {
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


}

