package com.example.crearrepositorio.features.series.domain.model

data class SerieModel(
    val id: Int,
    val name: String,
    val poster_path: String,
    val overview: String,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String
)


