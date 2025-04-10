package com.example.crearrepositorio.features.series.domain

import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getAllSeries(): Flow<List<SerieModel>>
    }
