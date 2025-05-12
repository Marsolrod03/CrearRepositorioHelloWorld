package com.example.crearrepositorio.features.series.domain

import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getPagedSeries(): Flow<Result<SeriesWrapper>>
    fun getSeriesDetails(seriesId: String): Flow<Result<SerieModel>>

}
