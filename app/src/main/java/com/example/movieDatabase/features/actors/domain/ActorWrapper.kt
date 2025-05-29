package com.example.movieDatabase.features.actors.domain

data class ActorWrapper (
    val hasMorePages: Boolean, val actorsList: List<ActorModel>, val isFirstPage: Boolean
)

