package com.example.domain.use_case

import com.example.domain.model.SerieModel
import com.example.domain.repository.SeriesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetSeriesDetailsUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke(seriesId: String): Flow<Result<SerieModel>> = seriesRepository.manageSeriesDetails(seriesId)
}