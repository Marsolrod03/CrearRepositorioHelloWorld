package com.example.crearrepositorio.features.series.data

import androidx.annotation.Keep
import com.example.crearrepositorio.features.series.domain.SerieModel

@Keep
data class SeriesDTO(
    val id: Int,
    val name: String,
    val poster_path: String,
    val overview: String,
    val vote_average: Double,
    val vote_count: Int,
    val first_air_date: String
)


@Keep
data class ResultsDTO(
    val page: Int,
    val results: List<SeriesDTO>,
    val total_pages: Int,
    val total_results: Int

)


