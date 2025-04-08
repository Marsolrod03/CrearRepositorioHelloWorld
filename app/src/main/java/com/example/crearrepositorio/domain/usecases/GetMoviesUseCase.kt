package com.example.crearrepositorio.domain.usecases

import com.example.crearrepositorio.data.implementations.MovieRepositoryImpl
import com.example.crearrepositorio.domain.entities.MovieModel
import kotlinx.coroutines.flow.Flow

class GetMoviesUseCase() {

    private val movieRepositoryImpl = MovieRepositoryImpl()

    fun getMovies(): Flow<List<MovieModel>>{
        return movieRepositoryImpl.createMovies()
    }
}