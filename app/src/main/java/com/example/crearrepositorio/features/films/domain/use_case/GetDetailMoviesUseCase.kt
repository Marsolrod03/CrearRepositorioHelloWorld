package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailMoviesUseCase @Inject constructor(
    private val moviesRepositoryImpl: MovieRepository
) {
    operator fun invoke(movieId: Int): Flow<Result<MovieModel>> = flow {
        moviesRepositoryImpl.getDetailMoviesFromApi(movieId)
            .collect { result ->
                result.onSuccess { movieModel ->
                    emit(Result.success(movieModel))
                }
                result.onFailure {
                    emit(Result.success(moviesRepositoryImpl.getDetailMoviesFromDatabase(movieId)))
                }
            }
    }
}