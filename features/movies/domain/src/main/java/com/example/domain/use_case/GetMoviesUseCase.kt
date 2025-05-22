package com.example.domain.use_case

import com.example.domain.MovieWrapper
import com.example.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<MovieWrapper>> = movieRepository.manageMoviesPagination()
}

