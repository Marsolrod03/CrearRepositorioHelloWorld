package com.example.movieDatabase.features.actors.data

import com.example.movieDatabase.features.actors.domain.ActorModel
import com.example.movieDatabase.features.actors.domain.ActorWrapper
import com.example.movieDatabase.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor
    (private val actorsNetworkDataSource: ActorsNetworkDataSource) : ActorsRepository {

    private var currentPage = 1
    private var isFirstPage = true
    private var listChange: MutableList<ActorModel> = mutableListOf()

    override fun getPagedActors(): Flow<Result<ActorWrapper>> = flow {
        try {
            val pagedResult = actorsNetworkDataSource.fetchActors(currentPage)
            pagedResult?.let {
                val actorList = it.results.map { actor -> actor.toActorModel() }
                listChange.addAll(actorList)
                val hasMorePages = it.page < it.total_pages
                val actorWrapper = ActorWrapper(hasMorePages, listChange, isFirstPage)
                currentPage ++
                isFirstPage = false
                emit(Result.success(actorWrapper))
            }?: run {
                val actorWrapper = ActorWrapper(false, emptyList(), false)
                emit(Result.success(actorWrapper))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }
}

