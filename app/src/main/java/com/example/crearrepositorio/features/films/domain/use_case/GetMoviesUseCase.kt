package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<MovieWrapper>> = movieRepository.manageMoviesPagination()
}

