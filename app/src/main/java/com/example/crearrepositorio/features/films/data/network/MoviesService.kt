package com.example.crearrepositorio.features.films.data.network

import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.data.dto.MoviePageDTO
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MoviePageDTO>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovies(
        @Path("movie_id") movieId: String
    ): Response<MovieDTO>
}