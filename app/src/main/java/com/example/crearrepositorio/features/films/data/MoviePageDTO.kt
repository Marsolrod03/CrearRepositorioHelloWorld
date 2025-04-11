package com.example.crearrepositorio.features.films.data

import kotlinx.serialization.Serializable

@Serializable
data class MoviePageDTO(
    val results: List<MovieDTO>,
)