package com.example.crearrepositorio.features.films.data

import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val overview: String,
    val poster_path: String,
    val title: String,
)