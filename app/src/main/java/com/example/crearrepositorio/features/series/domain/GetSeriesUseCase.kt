package com.example.crearrepositorio.features.series.domain

import com.example.crearrepositorio.features.series.data.SeriesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase() {
    private val repository = SeriesRepositoryImpl()
    suspend operator fun invoke(): Flow<List<SerieModel>>  { return repository.getAllSeries() }

}
