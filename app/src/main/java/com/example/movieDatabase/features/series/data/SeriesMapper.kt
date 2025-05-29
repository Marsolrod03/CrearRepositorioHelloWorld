package com.example.movieDatabase.features.series.data

import com.example.movieDatabase.features.series.domain.SerieModel

fun SeriesDTO.toSeriesModel(): SerieModel {
    val serieModel = SerieModel(
        name = name,
        poster_path = "https://image.tmdb.org/t/p/w500/$poster_path",
        overview = overview
    )
    return serieModel
}