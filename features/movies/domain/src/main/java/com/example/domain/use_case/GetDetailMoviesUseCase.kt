package com.example.domain.use_case

import com.example.domain.model.MovieModel
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDetailMoviesUseCase @Inject constructor(
    private val moviesRepositoryImpl: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Result<MovieModel>> =
        moviesRepositoryImpl.manageMovieDetails(movieId)
}