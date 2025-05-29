package com.example.movieDatabase.features.series.data

import androidx.annotation.Keep

@Keep
data class SeriesDTO(
    val name: String,
    val poster_path: String,
    val overview: String
)


@Keep
data class ResultsDTO(
    val page: Int,
    val results: List<SeriesDTO>,
    val total_pages: Int,
    val total_results: Int
)

