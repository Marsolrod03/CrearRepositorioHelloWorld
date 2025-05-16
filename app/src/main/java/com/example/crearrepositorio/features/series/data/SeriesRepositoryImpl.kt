package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity
import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.domain.SeriesRepository
import com.example.crearrepositorio.features.series.domain.SeriesWrapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SeriesRepositoryImpl @Inject constructor(
    private val networkDataSource: SeriesNetworkDataSource,
    private val databaseDataSource: DatabaseDataSource,
) : SeriesRepository {

    private var listChange: MutableList<SerieModel> = mutableListOf()

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
                val seriesWrapper = SeriesWrapper(false, emptyList())
                emit(Result.success(seriesWrapper))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))

        }
    }

    override fun getSeriesDetails(seriesId: String): Flow<Result<SerieModel>> = flow {
        try {
            val serieModel = networkDataSource.fetchDetails(seriesId)
            serieModel?.let {
                emit(Result.success(it))
            } ?: run {
                emit(Result.failure(AppError.UnknownError()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
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


}

