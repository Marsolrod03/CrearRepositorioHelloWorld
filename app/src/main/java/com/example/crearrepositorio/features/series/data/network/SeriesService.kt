package com.example.crearrepositorio.features.series.data.network

import com.example.crearrepositorio.features.series.data.ResultsDTO
import com.example.crearrepositorio.features.series.domain.SerieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {
    @GET("discover/tv")
    suspend fun getSeries(@Query("page") page: Int): Response<ResultsDTO>
    @GET("tv/{seriesId}")
    suspend fun seriesDetails(@Path("seriesId") seriesId: String): Response<SerieModel>


}