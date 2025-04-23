package com.example.crearrepositorio.features.films.data

import androidx.annotation.Keep

@Keep
data class MoviePageDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)