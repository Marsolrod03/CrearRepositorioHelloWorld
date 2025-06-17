package com.example.data.network

import com.example.data.dto.MovieDTO
import com.example.data.dto.MoviePageDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("region") region: String
    ): Response<MoviePageDTO>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovies(
        @Path("movie_id") movieId: Int
    ): Response<MovieDTO>
}