package com.example.data.data_source

import com.example.data.database.dao.ActorDao
import com.example.data.database.dao.PaginationActorsDao
import com.example.data.database.entities.ActorEntity
import com.example.data.toActorModel
import com.example.domain.models.ActorModel
import javax.inject.Inject

class ActorsLocalDataSource @Inject constructor(
    private val actorDao: ActorDao,
    private val paginationActorsDao: PaginationActorsDao
) {
    suspend fun getAllActors(): List<ActorEntity> = actorDao.getAllActors()
    suspend fun insertAllActors(actors: List<ActorEntity>) = actorDao.insertAllActors(actors)
    suspend fun deleteAllActors() = actorDao.deleteAllActors()
    suspend fun getActorById(id: Int): ActorModel = actorDao.getActorById(id).toActorModel()
    suspend fun updateActorBiography(id: Int, biography: String) =
        actorDao.updateActorBiography(id, biography)

    suspend fun getPaginationActors() = paginationActorsDao.getPaginationActors()
    suspend fun deletePaginationActors() = paginationActorsDao.clearPaginationActors()
    suspend fun updateLastPage(newPage: Int) = paginationActorsDao.updateLastLoadedPage(newPage)
    suspend fun insertPagination() = paginationActorsDao.insertPagination()
    suspend fun getLastDeletion(): Long = paginationActorsDao.getLastDeletion()
    suspend fun updateLastDeletion(lastDeletion: Long) = paginationActorsDao.updateLastDeletion(lastDeletion)
    suspend fun getTotalPages(): Int = paginationActorsDao.getTotalPages()
    suspend fun updateTotalPages(totalPages: Int) = paginationActorsDao.updateTotalPages(totalPages)
}