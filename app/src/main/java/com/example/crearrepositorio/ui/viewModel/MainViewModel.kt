package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel(){

    private val _estados = MutableStateFlow<UiState>(UiState.Idle)
    val stateHome : StateFlow<UiState> = _estados.asStateFlow()

    fun navigateToFilms(){
        _estados.update { currentState ->
            UiState.NavigateToFilms

        }
    }
    fun navigateToSeries(){
        _estados.update { currentState ->
            UiState.NavigateToSeries

        }
    }
    fun navigateToActors(){
        _estados.update { currentState ->
            UiState.NavigateToActors
        }
    }

    fun navigationCompleted() {
        _estados.update { currentState ->
            UiState.Idle
        }
    }
}

sealed class UiState{
    data object Idle: UiState()
    data object NavigateToFilms: UiState()
    data object NavigateToActors: UiState()
    data object NavigateToSeries: UiState()
}
