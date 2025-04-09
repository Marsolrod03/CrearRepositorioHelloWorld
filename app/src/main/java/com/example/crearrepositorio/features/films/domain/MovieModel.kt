package com.example.crearrepositorio.features.films.domain

data class MovieModel(
    val title: String,
    val cover: String,
    val genresName: List<String>
)