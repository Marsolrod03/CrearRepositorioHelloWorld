package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.data.SeriesDTO

fun SeriesDTO.toSeriesModel(): SerieModel {
    return SerieModel(
        name = name,
        poster_path = "https://image.tmdb.org/t/p/w500/$poster_path",
        overview = overview

    )
}