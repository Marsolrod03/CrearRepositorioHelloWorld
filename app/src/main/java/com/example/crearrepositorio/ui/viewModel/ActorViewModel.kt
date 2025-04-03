package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.crearrepositorio.R
import domain.ActorModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ActorViewModel : ViewModel(){

    private val _actorList = MutableStateFlow<List<ActorModel>>(emptyList())
    val actorList: StateFlow<List<ActorModel>> = _actorList

    init{
        loadList()
    }

    private fun loadList(){
        val actorList = listOf(
            ActorModel("Leonardo DiCaprio", R.drawable.actor_image),
            ActorModel("Denzel Washington", R.drawable.actor2_image),
            ActorModel("Will Smith", R.drawable.actor3_image),
            ActorModel("Ana De Armas", R.drawable.actor4_image),
            ActorModel("Jonny Depp", R.drawable.actor5_image),
            ActorModel("Tom Cruise", R.drawable.actor7_image),
            ActorModel("Brad Pitt", R.drawable.ic_launcher_background),
            ActorModel("Dwayne Johnson", R.drawable.ic_launcher_background),
            ActorModel("Morgan Freeman", R.drawable.ic_launcher_background),
            ActorModel("Keanu Reeves", R.drawable.ic_launcher_background),
            ActorModel("Jim Carrey", R.drawable.ic_launcher_background),
            ActorModel("Tom Hanks", R.drawable.ic_launcher_background),
            ActorModel("Liam Neeson", R.drawable.ic_launcher_background),
            ActorModel("Julia Roberts", R.drawable.ic_launcher_background),
            ActorModel("Jason Statham", R.drawable.ic_launcher_background)
        )
        _actorList.value = actorList
    }
}