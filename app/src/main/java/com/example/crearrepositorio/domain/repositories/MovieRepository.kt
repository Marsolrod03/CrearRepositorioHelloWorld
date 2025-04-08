package com.example.crearrepositorio.domain.repositories

import com.example.crearrepositorio.domain.entities.MovieModel

interface MovieRepository {
    suspend fun createMovies(): List<MovieModel>
}