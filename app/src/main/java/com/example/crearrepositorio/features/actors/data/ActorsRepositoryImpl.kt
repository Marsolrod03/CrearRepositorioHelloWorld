package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.common_data.RetrofitServiceFactory
import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor(): ActorsRepository {
    private val service = RetrofitServiceFactory.makeRetrofitService()

    override fun getActors(): Flow<List<ActorModel>> = flow {
        val response = service.listActors()
        if (response.isSuccessful) {
            response.body()?.results?.let { actors ->
                emit(actors.map { actor ->
                    actor.toActorModel()
                })
            }
        } else {
            throw Exception("Error charging the actors")
        }

    }
}