package com.example.crearrepositorio.features.actors.domain

import com.example.crearrepositorio.features.actors.domain.models.ActorModel

data class ActorWrapper (
    val hasMorePages: Boolean, val actorsList: List<ActorModel>
)

