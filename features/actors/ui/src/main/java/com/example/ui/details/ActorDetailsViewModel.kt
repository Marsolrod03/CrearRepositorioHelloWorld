package com.example.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ActorModel
import com.example.domain.use_cases.GetActorBiographyUseCase
import com.example.domain.use_cases.GetActorDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val getActorBiographyUseCase: GetActorBiographyUseCase
) : ViewModel() {

    private val _actorDetails = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val actorDetails: StateFlow<DetailsState> = _actorDetails.asStateFlow()

    fun resetStateToHome() {
        _actorDetails.value = DetailsState.Idle
    }

    fun loadDetails(actorId: String){
        viewModelScope.launch {
            getActorDetailsUseCase(actorId)
                .collect { result: Result<ActorModel> ->
                    result.onSuccess {actorModel: ActorModel ->
                        _actorDetails.value = DetailsState.Success(actorModel)
                        getActorBiographyUseCase.invoke(actorId.toInt(), actorModel.biography)
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