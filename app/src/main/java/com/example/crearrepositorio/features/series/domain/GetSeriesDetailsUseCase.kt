package com.example.crearrepositorio.features.series.domain

import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesDetailsUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    operator fun invoke(seriesId: String): Flow<Result<SerieModel>> {
        return seriesRepository.getSeriesDetails(seriesId)
    }
}