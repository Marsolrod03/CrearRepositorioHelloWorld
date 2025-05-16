package com.example.crearrepositorio.features.series.domain

import com.example.crearrepositorio.features.series.data.toSeriesModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeriesDetailsUseCase @Inject constructor(private val seriesRepository: SeriesRepository) {
    operator fun invoke(seriesId: String): Flow<Result<SerieModel>> = flow {
        val localSerie = seriesRepository.getSerieById(seriesId.toInt())
       seriesRepository.getSeriesDetails(seriesId)
           .collect{ result ->
               result.onSuccess { serie ->
                  emit(Result.success(serie))
               }
               result.onFailure {
                   if (localSerie != null) {
                       emit(Result.success(localSerie.toSeriesModel()))
                   }
               }
           }
    }
}