package com.example.crearrepositorio.features.actors.domain

import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActors(): Flow<Result<ActorWrapper>>
    fun getActorDetails(actorId: String): Flow<Result<ActorModel>>
}

