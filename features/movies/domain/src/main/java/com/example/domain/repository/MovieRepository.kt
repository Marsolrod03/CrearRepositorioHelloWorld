package com.example.domain.repository

import com.example.domain.MovieWrapper
import com.example.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getAllMoviesFromApi(page: Int): Flow<Result<MovieWrapper>>
     fun getDetailMoviesFromApi(movieId : Int): Flow<Result<MovieModel>>
     fun manageMoviesPagination(): Flow<Result<MovieWrapper>>
     fun manageMovieDetails(movieId : Int): Flow<Result<MovieModel>>
     suspend fun clearDatabase(timeRN : Long)
     suspend fun getAllMoviesFromDatabase(): List<MovieModel>
     suspend fun insertAllMovies(movies: List<MovieModel>)
     suspend fun clearMovieDatabase()
     suspend fun getDetailMoviesFromDatabase(movieId: Int): MovieModel
     suspend fun getLastMoviePage(): Int
     suspend fun deleteAllMoviePages()
     suspend fun updateLastLoadedPage(lastLoadedPage: Int)
     suspend fun getLastDelete(): Long
     suspend fun updateLastDelete(lastDelete: Long)
}
