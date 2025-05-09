package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor
    (private val actorsNetworkDataSource: ActorsNetworkDataSource) : ActorsRepository {

    private var currentPage = 1
    private var listChange: MutableList<ActorModel> = mutableListOf()

    override fun getPagedActors(): Flow<Result<ActorWrapper>> = flow {
        try {
            val pagedResult = actorsNetworkDataSource.fetchActors(currentPage)
            pagedResult?.let {
                val actorList = it.results.map { actor -> actor.toActorModel() }
                listChange.addAll(actorList)
                val hasMorePages = it.page < it.total_pages
                val actorWrapper = ActorWrapper(hasMorePages, listChange)
                currentPage ++
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


}

