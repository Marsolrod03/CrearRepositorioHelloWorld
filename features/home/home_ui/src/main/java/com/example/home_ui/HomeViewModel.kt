package com.example.home_ui

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

    private val _stateHome = MutableStateFlow(HomeState())
    val stateHome: StateFlow<HomeState> = _stateHome.asStateFlow()

    fun navigateTo(target: NavigationTarget) {
        _stateHome.update {
            it.copy(navigateTo = target)
        }
    }

    fun navigationCompleted() {
        _stateHome.update { it.copy(navigateTo = null) }
    }
}

data class HomeState(
    val navigateTo: NavigationTarget? = null
)


enum class NavigationTarget {
    FILMS, SERIES, ACTORS
}