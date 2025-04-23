package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.network.SeriesService
import com.example.crearrepositorio.common_data.RetrofitClient
import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.domain.SeriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SeriesRepositoryImpl : SeriesRepository {

    override suspend fun getAllSeries(): Flow<List<SerieModel>> {
        return flow {
            val client = RetrofitClient().retrofit
            val response = client.create(SeriesService::class.java).getSeries()
            if (!response.isSuccessful) {
                throw Exception("Error al obtener las series")
            } else {
                val datos = response.body()
                emit(datos?.results?.map { it.toSeriesModel() } ?: emptyList())
            }
        }
    }
}
