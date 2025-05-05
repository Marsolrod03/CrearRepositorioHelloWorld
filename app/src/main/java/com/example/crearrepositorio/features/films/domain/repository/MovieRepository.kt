package com.example.crearrepositorio.features.films.domain.repository

import com.example.crearrepositorio.features.films.domain.MovieWrapper
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
     fun createMovies(): Flow<Result<MovieWrapper>>
}
