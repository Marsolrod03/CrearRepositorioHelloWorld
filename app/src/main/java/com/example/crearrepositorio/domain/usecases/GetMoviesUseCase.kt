package com.example.crearrepositorio.domain.usecases

import com.example.crearrepositorio.data.implementations.MovieRepositoryImpl
import com.example.crearrepositorio.domain.entities.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetMoviesUseCase() {

    private val moviesRepo = MovieRepositoryImpl()

    fun getMovies(): Flow<List<MovieModel>>{
        return flow {
            emit(moviesRepo.createMovies())
        }
    }
}