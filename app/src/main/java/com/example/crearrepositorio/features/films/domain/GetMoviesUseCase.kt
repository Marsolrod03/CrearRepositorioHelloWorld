package com.example.crearrepositorio.features.films.domain

import com.example.crearrepositorio.features.films.data.MovieRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase() {

    private val movieRepositoryImpl = MovieRepositoryImpl()

    fun getMovies(): Flow<List<MovieModel>> {
        return movieRepositoryImpl.createMovies()
    }
}