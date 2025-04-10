package com.example.crearrepositorio.features.films.data

data class MoviePageDTO(
    val page: Int,
    val results: List<MovieDTO>,
    val total_pages: Int,
    val total_results: Int
)