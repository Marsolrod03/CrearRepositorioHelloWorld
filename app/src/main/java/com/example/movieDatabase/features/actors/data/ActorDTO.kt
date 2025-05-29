package com.example.movieDatabase.features.actors.data

import androidx.annotation.Keep

@Keep
data class ActorDTO(
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