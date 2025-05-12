package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.domain.SeriesRepository
import com.example.crearrepositorio.features.series.domain.SeriesWrapper
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SeriesRepositoryImpl @Inject constructor(
    private val networkDataSource: SeriesNetworkDataSource
) : SeriesRepository {

    private var currentPage = 1
    private var listChange: MutableList<SerieModel> = mutableListOf()

    override fun getPagedSeries(): Flow<Result<SeriesWrapper>> = flow {
        try {
            val pagedResult = networkDataSource.fetchSeries(currentPage)
            pagedResult?.let {
                val seriesList = pagedResult.results.map { series -> series.toSeriesModel() }
                listChange.addAll(seriesList)
                val hashMorePages = pagedResult.page < pagedResult.total_pages
                val seriesWrapper = SeriesWrapper(hashMorePages, listChange)
                currentPage++
                emit(Result.success(seriesWrapper))
            }?: run {
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
        }catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }
}

