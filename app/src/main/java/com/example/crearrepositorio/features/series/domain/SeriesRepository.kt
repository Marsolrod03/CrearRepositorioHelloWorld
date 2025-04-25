package com.example.crearrepositorio.features.series.domain

import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    suspend fun getAllSeries(): Flow<List<SerieModel>>
}
