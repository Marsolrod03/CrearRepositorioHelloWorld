package com.example.crearrepositorio.features.series.domain

import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getPagedSeries(): Flow<Result<SeriesWrapper>>
    fun getSeriesDetails(seriesId: String): Flow<Result<SerieModel>>
    suspend fun insertSeries(series: List<SeriesEntity>)
    suspend fun getAllSeriesFromDatabase(): List<SerieModel>

}
