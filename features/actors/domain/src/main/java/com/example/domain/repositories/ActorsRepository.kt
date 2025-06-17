package com.example.domain.repositories

import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActors(): Flow<Result<ActorWrapper>>
    suspend fun getActorDetails(actorId: String): Flow<Result<ActorModel>>
    suspend fun clearActors()
    suspend fun updateActorBiography(id: Int, biography: String)
    suspend fun clearPagination()
    suspend fun insertPagination()
    suspend fun getLastDeletion(): Long
    suspend fun updateLastDeletion(lastDeletion: Long)
}

