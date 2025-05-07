package com.example.crearrepositorio.features.actors.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.GetActorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val getActorsUseCase: GetActorsUseCase
) : ViewModel() {
    private val _actorList = MutableStateFlow<ActorState>(ActorState.Idle)
    val actorList: StateFlow<ActorState> = _actorList.asStateFlow()

    init {
        loadActors()
    }

    fun resetStateToHome() {
        _actorList.value = ActorState.ReturnHome
    }

    fun loadActors() {
        viewModelScope.launch {
            getActorsUseCase()
                .onStart {
                    if (_actorList.value is ActorState.PartialLoading ||
                        _actorList.value is ActorState.FirstLoading) {
                        emit(Result.failure(Throwable()))
                        return@onStart
                    }
                    _actorList.update {
                        if (_actorList.value is ActorState.Success &&
                            (_actorList.value as ActorState.Success).actors.isEmpty()) {
                            ActorState.FirstLoading
                        } else {
                            ActorState.PartialLoading
                        }
                    }
                }
                .collect { result ->
                    result.onSuccess { actorWrapper ->
                        _actorList.update {
                            ActorState.Success(actorWrapper.actorsList)
                        }
                    }
                    result.onFailure {
                        _actorList.update { ActorState.Error("Error loading more actors") }
                    }
                }
        }
    }
}

sealed class ActorState {
    data object Idle : ActorState()
    data object ReturnHome : ActorState()
    data class Success(val actors: List<ActorModel>) : ActorState()
    data class Error(val message: String) : ActorState()
    data object PartialLoading : ActorState()
    data object FirstLoading : ActorState()
}


