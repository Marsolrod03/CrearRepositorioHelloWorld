package com.example.data.dataSource

import com.example.data.database.dao.MovieDAO
import com.example.data.database.dao.MoviePageDAO
import com.example.data.database.entities.MovieEntity
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
    suspend fun getTotalPages(): Int = moviePageDAO.getTotalPages()
    suspend fun updateTotalPages(totalPages: Int) = moviePageDAO.updateTotalPages(totalPages)
}