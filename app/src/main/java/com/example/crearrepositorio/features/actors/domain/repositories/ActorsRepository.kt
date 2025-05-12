package com.example.crearrepositorio.features.actors.domain.repositories

import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.models.ActorModel
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActors(): Flow<Result<ActorWrapper>>
    fun getActorDetails(actorId: String): Flow<Result<ActorModel>>
}

