package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetDetailMoviesUseCase @Inject constructor(
    private val moviesRepositoryImpl: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Result<MovieModel>> =
        moviesRepositoryImpl.manageMovieDetails(movieId)
}