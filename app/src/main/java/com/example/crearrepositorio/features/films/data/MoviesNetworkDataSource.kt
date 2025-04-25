package com.example.crearrepositorio.features.films.data

import com.example.crearrepositorio.features.films.data.mapper.toMovieModel
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import jakarta.inject.Inject

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService
) {
    suspend fun fetchPopularMovies() : List<MovieModel> {
        val response = moviesService.getPopularMovies()

        if (!response.isSuccessful){
            throw Exception("Error: ${response.code()} ${response.message()}")
        }else{
            return response.body()?.results?.map { movie -> movie.toMovieModel()} ?: emptyList()
        }
    }
}