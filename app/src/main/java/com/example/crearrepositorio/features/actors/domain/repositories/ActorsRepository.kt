package com.example.crearrepositorio.features.actors.domain.repositories

import com.example.crearrepositorio.features.actors.data.database.entities.ActorEntity
import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.models.ActorModel
import kotlinx.coroutines.flow.Flow

interface ActorsRepository {
    fun getPagedActorsFromApi(currentPage: Int): Flow<Result<ActorWrapper>>
    suspend fun getActorsFromDatabase(): List<ActorModel>
    fun getActorDetails(actorId: String): Flow<Result<ActorModel>>
    suspend fun insertActors(actors: List<ActorModel>)
    suspend fun clearActors()
    suspend fun getActorById(id: Int): ActorEntity
    suspend fun updateActorBiography(id: Int, biography: String)
    suspend fun getPaginationActors(): Int
    suspend fun clearPagination()
    suspend fun updateLastPage(newPage: Int)
    suspend fun insertPagination(lastPage: Int)
}

