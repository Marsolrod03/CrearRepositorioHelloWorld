package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<MovieWrapper>> = flow {
        movieRepository.getAllMoviesFromApi().collect { result ->

            result.onSuccess { movieWrapper ->
                movieRepository.clearMovieDatabase()
                movieRepository.insertAllMovies(movieWrapper.movieList)
                emit(Result.success(movieWrapper))
            }

            result.onFailure {
                emit(Result.success(
                    MovieWrapper(false, movieRepository.getAllMoviesFromDatabase())
                ))
            }
        }
    }
}