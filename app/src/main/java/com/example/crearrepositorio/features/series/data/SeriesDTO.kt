package com.example.crearrepositorio.features.series.data

import kotlinx.serialization.Serializable

@Serializable
data class SeriesDTO(
    val name: String,
    val poster_path: String,
    val overview: String
)

@Serializable
data class ResultsDTO(
    val page: Int,
    val results: List<SeriesDTO>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class SeriesResponseDTO(
    val results: List<SeriesDTO>
)