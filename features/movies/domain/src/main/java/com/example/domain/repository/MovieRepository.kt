package com.example.domain.repository

import com.example.domain.MovieWrapper
import com.example.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun getDetailMoviesFromApi(movieId : Int): Flow<Result<MovieModel>>
     fun manageMoviesPagination(): Flow<Result<MovieWrapper>>
     fun manageMovieDetails(movieId : Int): Flow<Result<MovieModel>>
     suspend fun clearAndUpdateDatabase(timeRN : Long)
     suspend fun getLastDelete(): Long
}
