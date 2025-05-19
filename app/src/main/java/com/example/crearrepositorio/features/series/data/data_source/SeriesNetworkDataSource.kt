package com.example.crearrepositorio.features.series.data.data_source

import com.example.crearrepositorio.features.series.data.network.SeriesService
import com.example.crearrepositorio.features.series.data.network.model.ResultsDTO
import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.model.SerieModel
import retrofit2.Response
import javax.inject.Inject

class SeriesNetworkDataSource
@Inject
constructor(
    private val seriesService: SeriesService
) {
    suspend fun fetchSeries(page: Int): ResultsDTO? {
        val response = seriesService.getSeries(page)
        if (!response.isSuccessful) {
            throw handleErrors(response)
        } else {
            return response.body() ?: throw AppError.UnknownError()
        }
    }

    suspend fun fetchDetails(seriesId: String): SerieModel? {
        val response = seriesService.seriesDetails(seriesId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw handleErrors(response)
        }
    }

    fun <T> handleErrors(response: Response<T>): Throwable = when (response.code()) {
        401 -> AppError.Unauthorized
        403 -> AppError.Forbidden
        400 -> AppError.BadRequest
        404 -> AppError.NotFound
        in 500..599 -> AppError.ServerError
        else -> AppError.UnknownError()
    }
}
