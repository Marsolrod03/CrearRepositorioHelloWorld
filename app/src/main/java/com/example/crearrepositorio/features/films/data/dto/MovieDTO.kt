package com.example.crearrepositorio.features.films.data.dto

import androidx.annotation.Keep

@Keep
data class MovieDTO(
    val id:String,
    val overview: String,
    val poster_path: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Double,
    val release_date: String,
    val vote_average: Double
)