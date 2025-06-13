package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase @Inject constructor(
    private val movieRepositoryImpl: MovieRepository
) {

    suspend fun getMovies(): Flow<List<MovieModel>> {
        return movieRepositoryImpl.createMovies()
    }
}