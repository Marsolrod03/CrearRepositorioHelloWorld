package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.common_data.RetrofitClient
import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ActorsRepositoryImpl @Inject constructor(): ActorsRepository {

    override fun getActors(): Flow<List<ActorModel>> = flow {
        val service = RetrofitClient().retrofit
        val response = service.create(ActorsService::class.java).listActors()

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
