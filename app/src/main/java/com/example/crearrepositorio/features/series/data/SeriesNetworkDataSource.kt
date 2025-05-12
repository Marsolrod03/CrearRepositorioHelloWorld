package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.network.SeriesService
import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.SerieModel
import javax.inject.Inject

class SeriesNetworkDataSource
@Inject
constructor(
    private val seriesService: SeriesService
) {
    suspend fun fetchSeries(page: Int): ResultsDTO? {
        val response = seriesService.getSeries(page)
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
            return response.body() ?: throw AppError.UnknownError()
        }
    }

    suspend fun fetchDetails(seriesId: String): SerieModel? {
        val response = seriesService.seriesDetails(seriesId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw when (response.code()) {
                401 -> AppError.Unauthorized
                403 -> AppError.Forbidden
                400 -> AppError.BadRequest
                404 -> AppError.NotFound
                in 500..599 -> AppError.ServerError
                else -> AppError.UnknownError()
            }
        }
    }
}
