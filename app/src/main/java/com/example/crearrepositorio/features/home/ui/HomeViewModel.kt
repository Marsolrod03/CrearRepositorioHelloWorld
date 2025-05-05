package com.example.crearrepositorio.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val _stateHome = MutableStateFlow<UiState>(UiState.Idle)
    val stateHome: StateFlow<UiState> = _stateHome.asStateFlow()

    fun navigateToFilms() {
        viewModelScope.launch {
            _stateHome.update { UiState.Loading }
            delay(3000)
            _stateHome.update { UiState.Navigation.NavigateToFilms }
        }
    }

    fun navigateToSeries() {
        viewModelScope.launch {
            _stateHome.update { UiState.Loading }
            delay(3000)
            _stateHome.update { UiState.Navigation.NavigateToSeries }
        }
    }

    fun navigateToActors() {
        viewModelScope.launch {
            _stateHome.update { UiState.Loading }
            delay(3000)
            _stateHome.update { UiState.Navigation.NavigateToActors }
        }
    }

    fun navigationCompleted() {
        _stateHome.update { UiState.Idle }
    }
}

sealed class UiState {
    data object Idle : UiState()
    data object Loading : UiState()
    sealed class Navigation {
        data object NavigateToFilms : UiState()
        data object NavigateToActors : UiState()
        data object NavigateToSeries : UiState()
    }
}
