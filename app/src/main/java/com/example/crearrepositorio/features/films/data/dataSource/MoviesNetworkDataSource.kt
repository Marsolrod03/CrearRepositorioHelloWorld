package com.example.crearrepositorio.features.films.data.dataSource

import com.example.crearrepositorio.features.films.data.dto.MoviePageDTO
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.domain.model.MovieModel
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

    suspend fun fetchDetailMovies(movieId : String): MovieModel?{
        val response = moviesService.getDetailMovies(movieId)
        if (!response.isSuccessful) {
            throw Exception("Error al obtener las el detalle de las películas")
        }else{
            return response.body()
        }
    }
}