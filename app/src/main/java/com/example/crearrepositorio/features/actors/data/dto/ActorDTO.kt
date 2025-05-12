package com.example.crearrepositorio.features.actors.data.dto

import androidx.annotation.Keep

@Keep
data class ActorDTO(
    val id: Int,
    val name: String,
    val gender: Int,
    val popularity: Double,
    val profile_path: String?
)

@Keep
data class PagedResultDTO(
    val page: Int,
    val results: List<ActorDTO>,
    val total_pages: Int,
    val total_results: Int
)