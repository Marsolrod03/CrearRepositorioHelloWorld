package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.network.SeriesService
import com.example.crearrepositorio.common_data.RetrofitClient
import com.example.crearrepositorio.features.series.domain.AppError
import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.domain.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SeriesRepositoryImpl : SeriesRepository {

    override suspend fun getAllSeries(): Flow<List<SerieModel>> {
        return flow {
            val client = RetrofitClient().retrofit
            try {
                val response = client.create(SeriesService::class.java).getSeries()
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
                    emit(datos?.results?.map { it.toSeriesModel() } ?: emptyList())
                }
            }catch (e : Exception){
                e.printStackTrace()
                throw AppError.UnknownError(e.message)
            }

        }
    }

}
