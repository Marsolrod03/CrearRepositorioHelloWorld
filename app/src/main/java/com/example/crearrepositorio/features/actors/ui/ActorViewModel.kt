package com.example.crearrepositorio.features.actors.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.GetActorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActorViewModel : ViewModel() {
    private val _actorList = MutableStateFlow<ActorState>(ActorState.Idle)
    val actorList: StateFlow<ActorState> = _actorList.asStateFlow()
    private val getActorsUseCase = GetActorsUseCase()

    init {
        loadActors()
    }

    private fun loadActors() {
        viewModelScope.launch {
            getActorsUseCase()
                .catch {
                    _actorList.update { ActorState.Error("Error executing the application") }
                }
                .collect { list ->
                    _actorList.update { ActorState.Success(list) }
                }
        }
    }
}

sealed class ActorState {
    data object Idle : ActorState()
    data class Success(val actors: List<ActorModel>) : ActorState()
    data class Error(val message: String) : ActorState()
}


