package com.example.crearrepositorio.domain.entities

data class MovieModel(
    val title: String,
    val cover: String,
    val genresName: List<String>
)