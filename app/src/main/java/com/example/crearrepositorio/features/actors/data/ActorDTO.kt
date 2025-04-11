package com.example.crearrepositorio.features.actors.data

import kotlinx.serialization.Serializable

@Serializable
data class ActorDTO(
    val name: String,
    val gender: Int,
    val popularity: Double,
    val profile_path: String?
)

@Serializable
data class PagedResultDTO(
    val page: Int,
    val results: List<ActorDTO>,
    val total_pages: Int,
    val total_results: Int
)