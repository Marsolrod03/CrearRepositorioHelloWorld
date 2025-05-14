package com.example.crearrepositorio.features.series.domain.model

import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity
import com.example.crearrepositorio.features.series.domain.SerieModel

data class Serie(
    val id: Int,
    val name: String,
    val poster_path: String,
    val overview: String,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String
)

fun SerieModel.toDomain() =
    Serie(id, name, poster_path, overview, vote_average, vote_count, first_air_date)

fun SeriesEntity.toDomain() =
    Serie(id, name, poster_path, overview, vote_average, vote_count, first_air_date)



