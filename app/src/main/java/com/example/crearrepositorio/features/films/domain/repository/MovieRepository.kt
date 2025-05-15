package com.example.crearrepositorio.features.films.domain.repository

import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getAllMoviesFromApi(): Flow<Result<MovieWrapper>>
     suspend fun getAllMoviesFromDatabase(): List<MovieModel>
     suspend fun insertAllMovies(movies: List<MovieModel>)
     suspend fun clearMovieDatabase()
     fun getDetailMovies(movieId : String): Flow<Result<MovieModel>>

}
