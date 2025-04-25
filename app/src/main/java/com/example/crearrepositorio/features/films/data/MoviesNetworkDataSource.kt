package com.example.crearrepositorio.features.films.data

import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.data.mapper.toMovieModel
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.series.data.SeriesDTO
import com.example.crearrepositorio.features.series.data.network.SeriesService
import jakarta.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService){

    suspend fun fetchPopularMovies(): List<MovieModel> {
        val response = moviesService.getPopularMovies()
        if (!response.isSuccessful) {
            throw Exception("Error al obtener las peliculas")
        }else{
            return response.body()?.results?.map { it.toMovieModel() } ?: emptyList()

        }
    }
}