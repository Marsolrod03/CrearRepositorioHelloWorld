package com.example.crearrepositorio.domain.repositories

import com.example.crearrepositorio.domain.entities.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun createMovies(): Flow<List<MovieModel>>
}