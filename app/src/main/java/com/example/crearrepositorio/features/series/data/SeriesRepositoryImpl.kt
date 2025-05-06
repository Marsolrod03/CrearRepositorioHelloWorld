package com.example.crearrepositorio.features.series.data

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
    private var isFirstPage = true
    private var listChange: MutableList<SerieModel> = mutableListOf()

    override fun getPagedSeries(): Flow<Result<SeriesWrapper>> = flow {
        try {
            val pagedResult = networkDataSource.fetchSeries(currentPage)
            val seriesList = pagedResult.results.map { it.toSeriesModel() }
            listChange.addAll(seriesList)
            val hashMorePages = pagedResult.page < pagedResult.total_pages
            val seriesWrapper = SeriesWrapper(hashMorePages, listChange, isFirstPage)
            currentPage++
            isFirstPage = false
            emit(Result.success(seriesWrapper))
        } catch (e: Exception) {
            emit(Result.failure(e))

        }
    }
}

