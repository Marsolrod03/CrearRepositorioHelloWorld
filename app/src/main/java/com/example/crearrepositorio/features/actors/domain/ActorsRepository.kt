package com.example.crearrepositorio.features.actors.domain

interface ActorsRepository {
    fun getActors(): List<ActorModel>
}