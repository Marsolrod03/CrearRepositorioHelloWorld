package com.example.data.network

import com.example.data.network.model.ResultsDTO
import com.example.domain.model.SerieModel
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