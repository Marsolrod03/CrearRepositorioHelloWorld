package com.example.domain.use_case


import com.example.domain.SeriesWrapper
import com.example.domain.repository.SeriesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {

    suspend operator fun invoke(): Flow<Result<SeriesWrapper>> = seriesRepository.manageSeriesPagination()
}