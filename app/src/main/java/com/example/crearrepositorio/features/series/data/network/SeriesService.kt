package com.example.crearrepositorio.features.series.data.network

import com.example.crearrepositorio.features.series.data.ResultsDTO
import retrofit2.Response
import retrofit2.http.GET

interface SeriesService {
    @GET("discover/tv")
    suspend fun getSeries(): Response<ResultsDTO>

}