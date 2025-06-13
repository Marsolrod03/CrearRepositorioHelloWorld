package com.example.data

import com.example.data.data_source.ActorsLocalDataSource
import com.example.data.data_source.ActorsNetworkDataSource
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor(
    private val actorsNetworkDataSource: ActorsNetworkDataSource,
    private val actorsLocalDataSource: ActorsLocalDataSource
) : ActorsRepository {

    var currentPage = 0
    private var listChange: MutableList<ActorModel> = mutableListOf()

    override fun getPagedActors(): Flow<Result<ActorWrapper>> = flow {
        val lastPage = getPaginationActors()
        val actorsDataBase = getActorsFromDatabase()
        val totalPagesDataBase = getTotalPages()
        if (lastPage > currentPage) {
            currentPage = lastPage
            val hasMorePages = currentPage < totalPagesDataBase
            emit(Result.success(ActorWrapper(hasMorePages, actorsDataBase, totalPagesDataBase)))
        } else {
            getPagedActorsFromApi(currentPage + 1)
                .collect { result ->
                    result.onSuccess { actorWrapper ->
                        insertActors(actorWrapper.actorsList)
                        updateTotalPages(actorWrapper.totalPages)
                        currentPage ++
                        if(lastPage == 0){
                            insertPagination()
                        }
                        if (currentPage > lastPage) {
                            updateLastPage(currentPage)
                        }
                        emit(Result.success(actorWrapper))
                    }

                }
        }
    }

    override suspend fun getActorDetails(actorId: String): Flow<Result<ActorModel>> = flow {
        val localActor = getActorById(actorId.toInt())
        if (localActor.biography != "Info") {
            emit(Result.success(localActor))
        } else {
            getActorDetailsApi(actorId)
                .collect { result ->
                    result.onSuccess { actorModel ->
                        emit(Result.success(actorModel))
                    }
                    result.onFailure {
                        emit(Result.success(localActor))
                    }
                }
        }
    }

    fun getPagedActorsFromApi(currentPage: Int): Flow<Result<ActorWrapper>> = flow {
        try {
            var totalPages = Int.MAX_VALUE
            val pagedResult = actorsNetworkDataSource.fetchActors(currentPage)
            pagedResult?.let {
                val actorList = it.results.map { actor -> actor.toActorModel() }
                listChange.addAll(actorList)
                totalPages = it.total_pages
                val hasMorePages = it.page < totalPages
                val actorWrapper = ActorWrapper(hasMorePages, actorList, totalPages)
                emit(Result.success(actorWrapper))
            }?: run {
                val actorWrapper = ActorWrapper(false, emptyList(), totalPages)
                emit(Result.success(actorWrapper))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    fun getActorDetailsApi(actorId: String): Flow<Result<ActorModel>> = flow {
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

    suspend fun getActorsFromDatabase(): List<ActorModel>{
        return actorsLocalDataSource.getAllActors().map { entity ->  entity.toActorModel()}
    }

    private suspend fun insertActors(actors: List<ActorModel>) {
        actorsLocalDataSource.insertAllActors(actors.map { model -> model.toActorEntity() })
    }

    override suspend fun clearActors() {
        actorsLocalDataSource.deleteAllActors()
    }

    private suspend fun getActorById(id: Int): ActorModel {
        return actorsLocalDataSource.getActorById(id)
    }

    override suspend fun updateActorBiography(id: Int, biography: String) {
        actorsLocalDataSource.updateActorBiography(id, biography)
    }

    private suspend fun getPaginationActors(): Int {
        return actorsLocalDataSource.getPaginationActors()
    }

    override suspend fun clearPagination() {
        actorsLocalDataSource.deletePaginationActors()
    }

    private suspend fun updateLastPage(newPage: Int){
        actorsLocalDataSource.updateLastPage(newPage)
    }

    override suspend fun insertPagination() {
        actorsLocalDataSource.insertPagination()
    }

    override suspend fun getLastDeletion(): Long {
        return actorsLocalDataSource.getLastDeletion()
    }

    override suspend fun updateLastDeletion(lastDeletion: Long) {
        actorsLocalDataSource.updateLastDeletion(lastDeletion)
    }

    private suspend fun getTotalPages(): Int {
        return actorsLocalDataSource.getTotalPages()
    }

    private suspend fun updateTotalPages(totalPages: Int) {
        actorsLocalDataSource.updateTotalPages(totalPages)
    }
}

