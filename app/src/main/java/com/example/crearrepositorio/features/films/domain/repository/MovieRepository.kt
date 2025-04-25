package com.example.crearrepositorio.features.films.domain.repository

import com.example.crearrepositorio.features.films.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun createMovies(): Flow<List<MovieModel>>
}
