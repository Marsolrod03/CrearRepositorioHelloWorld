package com.example.movieDatabase.features.series.domain

import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    suspend fun getAllSeries(): Flow<List<SerieModel>>
}
