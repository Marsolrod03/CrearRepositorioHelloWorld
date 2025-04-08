package domain

import domain.models.ActorModel

interface ActorsRepository {
    fun getActors(): List<ActorModel>
}