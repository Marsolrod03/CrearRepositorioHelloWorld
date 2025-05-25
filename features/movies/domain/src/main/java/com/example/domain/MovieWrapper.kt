package com.example.domain

import com.example.domain.model.MovieModel

data class MovieWrapper (
    val hasMorePages: Boolean, val movieList: List<MovieModel>, val totalPages: Int
)