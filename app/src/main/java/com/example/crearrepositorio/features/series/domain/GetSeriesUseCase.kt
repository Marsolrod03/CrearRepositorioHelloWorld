package com.example.crearrepositorio.features.series.domain

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(): Flow<List<SerieModel>>  { return seriesRepository.getAllSeries() }

}
