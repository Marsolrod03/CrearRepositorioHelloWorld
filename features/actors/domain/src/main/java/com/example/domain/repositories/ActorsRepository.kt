package com.example.domain.repositories

import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActors(): Flow<Result<ActorWrapper>>
    suspend fun getActorDetails(actorId: String): Flow<Result<ActorModel>>
    fun getPagedActorsFromApi(currentPage: Int): Flow<Result<ActorWrapper>>
    suspend fun getActorsFromDatabase(): List<ActorModel>
    fun getActorDetailsApi(actorId: String): Flow<Result<ActorModel>>
    suspend fun insertActors(actors: List<ActorModel>)
    suspend fun clearActors()
    suspend fun getActorById(id: Int): ActorModel
    suspend fun updateActorBiography(id: Int, biography: String)
    suspend fun getPaginationActors(): Int
    suspend fun clearPagination()
    suspend fun updateLastPage(newPage: Int)
    suspend fun insertPagination()
    suspend fun getLastDeletion(): Long
    suspend fun updateLastDeletion(lastDeletion: Long)
    suspend fun getTotalPages(): Int
    suspend fun updateTotalPages(totalPages: Int)
}

