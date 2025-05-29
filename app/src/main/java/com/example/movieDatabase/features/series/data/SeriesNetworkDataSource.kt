package com.example.movieDatabase.features.series.data

import com.example.movieDatabase.features.series.data.network.SeriesService
import com.example.movieDatabase.features.series.domain.AppError
import jakarta.inject.Inject

class SeriesNetworkDataSource
@Inject
constructor(
    private val seriesService: SeriesService
){

    suspend fun fetchSeries(): List<SeriesDTO> {
        try {
            val response = seriesService.getSeries()
            if (!response.isSuccessful) {
                throw when (response.code()) {
                    401 -> AppError.Unauthorized
                    403 -> AppError.Forbidden
                    400 -> AppError.BadRequest
                    404 -> AppError.NotFound
                    in 500..599 -> AppError.ServerError
                    else -> AppError.UnknownError()
                }
            } else {
                val datos = response.body()
                return datos?.results?: emptyList()
            }
        }catch (e : Exception){
            e.printStackTrace()
            throw AppError.UnknownError(e.message)
        }
    }
}