package com.example.movieDatabase.features.actors.domain

import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActors(): Flow<Result<ActorWrapper>>
}

