package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.Gender

fun ActorDTO.toActorModel(): ActorModel {
    val genderAux = when (gender){
        1 -> Gender.Female
        2 -> Gender.Male
        else -> Gender.Unknown
    }

    val actorModel = ActorModel(
        id = id,
        name = name,
        gender = genderAux,
        image = "https://image.tmdb.org/t/p/w500/$profile_path",
        popularity = popularity
    )
    return actorModel
}