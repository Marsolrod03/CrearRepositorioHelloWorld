package com.example.crearrepositorio.domain

import com.example.crearrepositorio.domain.models.ActorModel

interface ActorsRepository {
    fun getActors(): List<ActorModel>
}