package com.example.crearrepositorio.features.actors.domain

import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getActors(): Flow<List<ActorModel>>
}

