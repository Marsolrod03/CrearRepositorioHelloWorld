package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.data.data_source.ActorsLocalDataSource
import com.example.crearrepositorio.features.actors.data.data_source.ActorsNetworkDataSource
import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.models.ActorModel
import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor(
    private val actorsNetworkDataSource: ActorsNetworkDataSource,
    private val actorsLocalDataSource: ActorsLocalDataSource
) : ActorsRepository {

    private var listChange: MutableList<ActorModel> = mutableListOf()

    override fun getPagedActorsFromApi(currentPage: Int): Flow<Result<ActorWrapper>> = flow {
        try {
            val pagedResult = actorsNetworkDataSource.fetchActors(currentPage)
            pagedResult?.let {
                val actorList = it.results.map { actor -> actor.toActorModel() }
                listChange.addAll(actorList)
                val hasMorePages = it.page < it.total_pages
                val actorWrapper = ActorWrapper(hasMorePages, actorList)
                emit(Result.success(actorWrapper))
            }?: run {
                val actorWrapper = ActorWrapper(false, emptyList())
                emit(Result.success(actorWrapper))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override fun getActorDetails(actorId: String): Flow<Result<ActorModel>> = flow {
        try {
            val actorModel = actorsNetworkDataSource.fetchDetails(actorId)
            actorModel?.let {
                emit(Result.success(it))
            }?: run{
                emit(Result.failure(Exception("Actor details not found")))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    override suspend fun getActorsFromDatabase(): List<ActorModel>{
        return actorsLocalDataSource.getAllActors().map { entity ->  entity.toActorModel()}
    }

    override suspend fun insertActors(actors: List<ActorModel>) {
        actorsLocalDataSource.insertAllActors(actors.map { model -> model.toActorEntity() })
    }

    override suspend fun clearActors() {
        actorsLocalDataSource.deleteAllActors()
    }

    override suspend fun getActorById(id: Int): ActorModel {
        return actorsLocalDataSource.getActorById(id)
    }

    override suspend fun updateActorBiography(id: Int, biography: String) {
        actorsLocalDataSource.updateActorBiography(id, biography)
    }

    override suspend fun getPaginationActors(): Int {
        return actorsLocalDataSource.getPaginationActors()
    }

    override suspend fun clearPagination() {
        actorsLocalDataSource.deletePaginationActors()
    }

    override suspend fun updateLastPage(newPage: Int){
        actorsLocalDataSource.updateLastPage(newPage)
    }

    override suspend fun insertPagination(lastPage: Int) {
        actorsLocalDataSource.insertPagination(lastPage)
    }
}

