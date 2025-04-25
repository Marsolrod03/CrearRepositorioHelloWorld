package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor
    (private val actorsNetworkDataSource: ActorsNetworkDataSource) : ActorsRepository {

    override fun getActors(): Flow<List<ActorModel>> = flow {
        val result = actorsNetworkDataSource.fetchActors()
        emit(result.map { it.toActorModel() })

    }
}
