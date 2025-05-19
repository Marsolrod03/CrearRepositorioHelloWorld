package com.example.crearrepositorio.features.home.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {

    private val _stateHome = MutableStateFlow<UiState>(UiState.Idle)
    val stateHome: StateFlow<UiState> = _stateHome.asStateFlow()

    fun navigateToFilms() {
            _stateHome.update { UiState.Navigation.NavigateToFilms }
    }

    fun navigateToSeries() {
            _stateHome.update { UiState.Navigation.NavigateToSeries }
        }

    fun navigateToActors() {
            _stateHome.update { UiState.Navigation.NavigateToActors }
        }


    fun navigationCompleted() {
        _stateHome.update { UiState.Idle }
    }
}

sealed class UiState {
    data object Idle : UiState()
    sealed class Navigation {
        data object NavigateToFilms : UiState()
        data object NavigateToActors : UiState()
        data object NavigateToSeries : UiState()
    }
}
