package com.example.crearrepositorio.features.films.data.dataSource

import com.example.crearrepositorio.features.films.data.database.dao.MovieDAO
import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val movieDAO: MovieDAO
) {
    suspend fun getAllMovies(): List<MovieEntity> = movieDAO.getAllMovies()
    suspend fun insertAllMovies(movies: List<MovieEntity>) = movieDAO.insertAllMovies(movies)
    suspend fun clearDatabase() = movieDAO.deleteAllMovies()
    suspend fun getMovieDetails(movieId: Int) = movieDAO.getMovieDetails(movieId)
}