package com.example.domain.use_case

import com.example.domain.repository.SeriesRepository
import javax.inject.Inject


class DeleteDatabaseUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    suspend operator fun invoke() = seriesRepository.refreshData()
}