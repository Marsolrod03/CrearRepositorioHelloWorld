package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.Gender

fun ActorDTO.toActorModel(): ActorModel {
    val genderAux = when (gender){
        1 -> Gender.FEMALE
        2 -> Gender.MALE
        else -> Gender.UNKNOWN
    }

    val actorModel = ActorModel(
        name = name,
        gender = genderAux,
        image = "https://inkscape.app/wp-content/uploads/imagen-vectorial.webp",
        popularity = popularity
    )
    return actorModel
}