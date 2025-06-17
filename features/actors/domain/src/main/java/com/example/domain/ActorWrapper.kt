package com.example.domain

import com.example.domain.models.ActorModel

data class ActorWrapper (
    val hasMorePages: Boolean, val actorsList: List<ActorModel>, val totalPages: Int
)

