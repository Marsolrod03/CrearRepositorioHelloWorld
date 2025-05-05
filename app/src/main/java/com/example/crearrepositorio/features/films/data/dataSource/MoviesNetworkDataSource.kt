package com.example.crearrepositorio.features.films.data.dataSource

import com.example.crearrepositorio.features.films.data.dto.MoviePageDTO
import com.example.crearrepositorio.features.films.data.network.MoviesService
import jakarta.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService
){

    suspend fun fetchPopularMovies(page : Int): MoviePageDTO? {
        val response = moviesService.getPopularMovies(page)
        if (!response.isSuccessful) {
            throw Exception("Error al obtener las peliculas")
        }else{
            return response.body()
        }
    }
}