package com.example.crearrepositorio.features.films.domain.model


data class MovieModel(
    val id: String,
    val overview: String,
    val poster_path: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double
)