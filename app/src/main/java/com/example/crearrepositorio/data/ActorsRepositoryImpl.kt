package com.example.crearrepositorio.data

import com.example.crearrepositorio.R
import com.example.crearrepositorio.domain.ActorsRepository
import com.example.crearrepositorio.domain.models.ActorModel

class ActorsRepositoryImpl: ActorsRepository {
    override fun getActors(): List<ActorModel> {
        return listOf(
            ActorModel("Leonardo DiCaprio", R.drawable.actor_image, "Info"),
            ActorModel("Denzel Washington", R.drawable.actor2_image, "Info"),
            ActorModel("Will Smith", R.drawable.actor3_image, "Info"),
            ActorModel("Ana De Armas", R.drawable.actor4_image, "Info"),
            ActorModel("Jonny Depp", R.drawable.actor5_image, "Info"),
            ActorModel("Tom Cruise", R.drawable.actor7_image, "Info"),
            ActorModel("Brad Pitt", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Dwayne Johnson", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Morgan Freeman", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Keanu Reeves", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Jim Carrey", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Tom Hanks", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Liam Neeson", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Julia Roberts", R.drawable.ic_launcher_background, "Info"),
            ActorModel("Jason Statham", R.drawable.ic_launcher_background, "Info")
        )
    }
}