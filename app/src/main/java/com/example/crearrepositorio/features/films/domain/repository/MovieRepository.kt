package com.example.crearrepositorio.features.films.domain.repository

import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun createMovies(): Flow<Result<MovieWrapper>>
     fun getDetailMovies(movieId : String): Flow<Result<MovieModel>>
}
