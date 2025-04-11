package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ActorsRepositoryImpl: ActorsRepository {

    override fun getActors(): Flow<List<ActorModel>> = flow {
        val jsonName = "people.json"
        val actorsDTO = readJsonActors(jsonName)

        emit(actorsDTO.map { it.toActorModel() })
    }

}