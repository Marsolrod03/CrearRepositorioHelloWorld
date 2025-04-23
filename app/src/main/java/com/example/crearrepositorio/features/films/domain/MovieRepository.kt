package com.example.crearrepositorio.features.films.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun createMovies(): Flow<List<MovieModel>>
}