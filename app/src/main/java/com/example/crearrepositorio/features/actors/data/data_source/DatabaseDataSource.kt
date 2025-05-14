package com.example.crearrepositorio.features.actors.data.data_source

import com.example.crearrepositorio.features.actors.data.database.dao.ActorDao
import com.example.crearrepositorio.features.actors.data.database.dao.PaginationActorsDao
import com.example.crearrepositorio.features.actors.data.database.entities.ActorEntity
import javax.inject.Inject

class DatabaseDataSource @Inject constructor(
    private val actorDao: ActorDao,
    private val paginationActorsDao: PaginationActorsDao
) {
    suspend fun getAllActors(): List<ActorEntity> = actorDao.getAllActors()
    suspend fun insertAllActors(actors: List<ActorEntity>) = actorDao.insertAllActors(actors)
    suspend fun deleteAllActors() = actorDao.deleteAllActors()

    suspend fun getPaginationActors() = paginationActorsDao.getPaginationActors()
    suspend fun deletePaginationActors() = paginationActorsDao.clearPaginationActors()
    suspend fun updateLastPage(newPage: Int) = paginationActorsDao.updateLastLoadedPage(newPage)
    suspend fun insertPagination(pageLoaded: Int) = paginationActorsDao.insertPagination(pageLoaded)
}