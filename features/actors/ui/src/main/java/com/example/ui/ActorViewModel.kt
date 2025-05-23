package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.use_cases.GetActorsUseCase
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
    private var hasMorePages = true

    init {
        loadActors()
    }

    fun resetStateToHome() {
        _actorList.value = ActorState.Idle
    }

    fun loadActors() {
        if (hasMorePages &&
            _actorList.value !is ActorState.PartialLoading &&
            _actorList.value !is ActorState.FirstLoading
        ) {
            viewModelScope.launch {
                getActorsUseCase()
                    .onStart {
                        _actorList.update {
                            if ((_actorList.value as? ActorState.Success)?.actors?.isNotEmpty() == true) {
                                ActorState.PartialLoading
                            } else {
                                ActorState.FirstLoading
                            }
                        }
                    }
                    .collect { result: Result<ActorWrapper> ->
                        result.onSuccess { actorWrapper: ActorWrapper ->
                            hasMorePages = actorWrapper.hasMorePages
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
}

sealed class ActorState {
    data object Idle : ActorState()
    data class Success(val actors: List<ActorModel>) : ActorState()
    data class Error(val message: String) : ActorState()
    data object PartialLoading : ActorState()
    data object FirstLoading : ActorState()
}


