package com.example.crearrepositorio.features.series.domain


import com.example.crearrepositorio.features.series.data.toSeriesEntity
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeriesUseCase @Inject constructor(
    private val seriesRepository: SeriesRepository
) {
    operator fun invoke(): Flow<Result<SeriesWrapper>> = flow {
        seriesRepository.getPagedSeries()
            .collect { result ->
                result.onSuccess { seriesWrapper ->
                    seriesRepository.insertSeries(seriesWrapper.listSeries.map { it.toSeriesEntity() })
                    emit(Result.success(seriesWrapper))
                }
                result.onFailure {
                    emit(
                        Result.success(
                            SeriesWrapper(
                                false,
                                seriesRepository.getAllSeriesFromDatabase()
                            )
                        )
                    )

                }
            }
    }
}
