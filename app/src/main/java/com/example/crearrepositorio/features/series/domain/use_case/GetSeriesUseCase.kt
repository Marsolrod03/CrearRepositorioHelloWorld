package com.example.crearrepositorio.features.series.domain.use_case


import com.example.crearrepositorio.features.series.domain.SeriesWrapper
import com.example.crearrepositorio.features.series.domain.repository.SeriesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {

    suspend operator fun invoke(): Flow<Result<SeriesWrapper>> = seriesRepository.manageSeriesPagination()
}