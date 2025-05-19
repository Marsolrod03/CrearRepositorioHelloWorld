package com.example.crearrepositorio.features.series.data

import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity
import com.example.crearrepositorio.features.series.data.network.model.SeriesDTO
import com.example.crearrepositorio.features.series.domain.model.SerieModel

fun SeriesDTO.toSeriesModel(): SerieModel {
    return SerieModel(
        id = id,
        name = name,
        poster_path = "https://image.tmdb.org/t/p/w500/$poster_path",
        overview = overview,
        vote_average = vote_average,
        vote_count = vote_count,
        first_air_date = first_air_date
    )
}

fun SerieModel.toSeriesEntity(): SeriesEntity {
    return SeriesEntity(
        id = id,
        name = name,
        poster_path = poster_path,
        overview = overview,
        vote_average = vote_average,
        vote_count = vote_count,
        first_air_date = first_air_date
    )
}

fun SeriesEntity.toSeriesModel(): SerieModel {
    return SerieModel(
        id = id,
        name = name,
        poster_path = poster_path,
        overview = overview,
        vote_average = vote_average,
        vote_count = vote_count,
        first_air_date = first_air_date
    )
}