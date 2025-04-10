package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.ActorsRepository

class ActorsRepositoryImpl: ActorsRepository {

    override fun getActors(): List<ActorModel> {
        val jsonName = "people.json"
        val actorsDTO = readJson(jsonName)

         return actorsDTO.map {
            it.toActorModel()
        }
    }

}