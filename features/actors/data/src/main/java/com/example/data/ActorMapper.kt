package com.example.data

import com.example.data.database.entities.ActorEntity
import com.example.data.dto.ActorDTO
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender

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

fun ActorEntity.toActorModel(): ActorModel {
    val actorModel = ActorModel(
        id = id,
        name = name,
        gender = gender,
        image = image,
        popularity = popularity,
        biography = biography
    )
    return actorModel
}

fun ActorModel.toActorEntity(): ActorEntity {
    val actorEntity = ActorEntity(
        id = id,
        name = name,
        gender = gender,
        image = image,
        popularity = popularity,
        biography = biography
    )
    return actorEntity
}
