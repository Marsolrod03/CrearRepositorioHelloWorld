package com.example.crearrepositorio.features.films.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun createMovies(): Flow<List<MovieModel>>
}