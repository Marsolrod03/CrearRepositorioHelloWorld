package com.example.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.use_cases.GetActorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val getActorsUseCase: GetActorsUseCase,
    private val stringResourceProvider: StringResourceProvider
) : ViewModel() {
    val _actorList = MutableStateFlow(ActorState())
    val actorList: StateFlow<ActorState> = _actorList.asStateFlow()
    var hasMorePages = true
    var _currentActors = mutableListOf<ActorModel>()

    init {
        loadActors()
    }

    fun resetStateToHome() {
        _actorList.value = ActorState()
        hasMorePages = true
        _currentActors.clear()
    }

    fun loadActors() {
        val currentState = _actorList.value
        if (hasMorePages && !currentState.isPartialLoading && !currentState.isFirstLoad) {
            viewModelScope.launch {
                getActorsUseCase.invoke()
                    .onStart {
                        _actorList.update { current ->
                            if (_currentActors.isNotEmpty()) {
                                current.copy(isPartialLoading = true, isFirstLoad = false)
                            } else {
                                current.copy(isPartialLoading = true, isFirstLoad = true)
                            }
                        }
                    }
                    .catch {
                        it.message
                        Log.d("Error", "ERRO")
                        println(it.message)
                    }
                    .collect { result: Result<ActorWrapper> ->
                        result.onSuccess { actorWrapper: ActorWrapper ->
                            hasMorePages = actorWrapper.hasMorePages
                            _currentActors.addAll(actorWrapper.actorsList)

                            _actorList.update { current ->
                                current.copy(
                                    isPartialLoading = false,
                                    isFirstLoad = false,
                                    actors = _currentActors.toList(),
                                    errorMessage = null
                                )
                            }
                        }
                        result.onFailure {
                            _actorList.update { current ->
                                current.copy(
                                    isPartialLoading = false,
                                    isFirstLoad = false,
                                    errorMessage = stringResourceProvider.getString(R.string.errorActors)
                                )
                            }
                        }
                    }
            }
        }
    }
}
data class ActorState(
    val isPartialLoading: Boolean = false,
    val isFirstLoad: Boolean = false,
    var actors: List<ActorModel> = emptyList(),
    val errorMessage: String? = null
)


