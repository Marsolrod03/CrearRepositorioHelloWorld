package com.example.crearrepositorio.features.actors.domain

data class ActorModel(
    val id: Int,
    val name: String,
    val image: String,
    val gender: Gender,
    val popularity: Double,
    val biography: String = "Info"
)

enum class Gender{
    Male, Female, Unknown
}