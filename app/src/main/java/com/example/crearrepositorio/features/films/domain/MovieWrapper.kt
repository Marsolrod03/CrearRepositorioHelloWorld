package com.example.crearrepositorio.features.films.domain

import com.example.crearrepositorio.features.films.domain.model.MovieModel

data class MovieWrapper (
    val hasMorePages: Boolean, val movieList: List<MovieModel>
)