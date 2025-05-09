package com.example.crearrepositorio.features.actors.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.actors.domain.ActorModel
import com.example.crearrepositorio.features.actors.domain.GetActorDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase
) : ViewModel() {

    private val _actorDetails = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val actorDetails: StateFlow<DetailsState> = _actorDetails.asStateFlow()

    private var currentActorId: String? = null

    fun resetStateToHome() {
        _actorDetails.value = DetailsState.Idle
    }

    fun loadDetails(actorId: String){
        currentActorId = actorId

        viewModelScope.launch {
            getActorDetailsUseCase(actorId)
                .collect { result ->
                    result.onSuccess {actorModel ->
                        _actorDetails.value = DetailsState.Success(actorModel)
                    }
                    result.onFailure {
                        _actorDetails.value = DetailsState.Error("Error loading actor details")
                    }
                }
        }
    }
}

sealed class DetailsState{
    data object Idle : DetailsState()
    data class Success(val details: ActorModel) : DetailsState()
    data class Error(val message: String) : DetailsState()
}