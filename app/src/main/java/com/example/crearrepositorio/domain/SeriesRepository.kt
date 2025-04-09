package com.example.crearrepositorio.domain

import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getAllSeries(): Flow<List<SerieModel>>
    }
