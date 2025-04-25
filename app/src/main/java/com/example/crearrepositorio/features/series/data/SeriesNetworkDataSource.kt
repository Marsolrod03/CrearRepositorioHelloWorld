package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.network.SeriesService
import jakarta.inject.Inject

class SeriesNetworkDataSource @Inject constructor(
    private val seriesService: SeriesService){

    suspend fun fetchSeries(): List<SeriesDTO> {
        val response = seriesService.getSeries()
        if (!response.isSuccessful) {
            throw Exception("Error al obtener las series")
        }else{
            return response.body()?.results ?: emptyList()

        }
    }
}