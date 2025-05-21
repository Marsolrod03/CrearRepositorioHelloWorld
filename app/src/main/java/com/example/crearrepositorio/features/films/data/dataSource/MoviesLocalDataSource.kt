package com.example.crearrepositorio.features.films.data.dataSource

import com.example.crearrepositorio.features.films.data.database.dao.MovieDAO
import com.example.crearrepositorio.features.films.data.database.dao.MoviePageDAO
import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import com.example.crearrepositorio.features.films.data.database.entities.MoviePageEntity
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val movieDAO: MovieDAO,
    private val moviePageDAO: MoviePageDAO
) {
    suspend fun getAllMovies(): List<MovieEntity> = movieDAO.getAllMovies()
    suspend fun insertAllMovies(movies: List<MovieEntity>) = movieDAO.insertAllMovies(movies)
    suspend fun clearDatabase() = movieDAO.deleteAllMovies()
    suspend fun getMovieDetails(movieId: Int) = movieDAO.getMovieDetails(movieId)

    suspend fun getLastMoviePage(): Int = moviePageDAO.getLastMoviePage()
    suspend fun deleteAllMoviePages() = moviePageDAO.deleteAllMoviePages()

    suspend fun updateLastLoadedPage(lastLoadedPage: Int) =
        moviePageDAO.updateLastLoadedPage(lastLoadedPage)

    suspend fun getLastDeleteDB() = moviePageDAO.getLastDelete()
    suspend fun updateLastDeleteDB(lastDelete: Long) = moviePageDAO.updateLastDelete(lastDelete)
}