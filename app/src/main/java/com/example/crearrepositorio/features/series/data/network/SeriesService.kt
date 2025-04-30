package com.example.crearrepositorio.features.series.data.network

import com.example.crearrepositorio.features.series.data.ResultsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesService {
    @GET("discover/tv")
    suspend fun getSeries(@Query("page") page: Int): Response<ResultsDTO>

}