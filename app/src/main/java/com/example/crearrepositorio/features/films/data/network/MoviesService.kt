package com.example.crearrepositorio.features.films.data.network

import com.example.crearrepositorio.features.films.data.dto.MoviePageDTO
import retrofit2.Response
import retrofit2.http.GET

interface MoviesService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MoviePageDTO>
}