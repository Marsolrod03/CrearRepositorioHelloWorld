package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor
    (private val actorsNetworkDataSource: ActorsNetworkDataSource) : ActorsRepository {

    private var currentPage: Int = 1

    override fun getPagedActors(): Flow<Result<ActorWrapper>> = flow {
        try {
            val pagedResult = actorsNetworkDataSource.fetchActors(currentPage)
            pagedResult?.let {
                val actorList = it.results.map { actor -> actor.toActorModel() }
                val hashMorePages = it.page < it.total_pages
                val actorWrapper = ActorWrapper(hashMorePages, actorList)
                currentPage ++
                emit(Result.success(actorWrapper))
            }?: run {
                val actorWrapper = ActorWrapper(false, emptyList())
                emit(Result.success(actorWrapper))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

