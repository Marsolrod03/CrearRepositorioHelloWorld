package com.example.crearrepositorio.features.series.domain

import androidx.paging.PagingData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(): Flow<Result<SeriesWrapper>> {
        return seriesRepository.getPagedSeries()
    }
}
