package com.example.crearrepositorio.features.films.data.dto

import androidx.annotation.Keep

@Keep
data class MovieDTO(
    val overview: String,
    val poster_path: String,
    val title: String,
)