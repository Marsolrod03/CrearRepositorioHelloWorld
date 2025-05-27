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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailsViewModel @Inject constructor(
    private val getActorDetailsUseCase: GetActorDetailsUseCase,
    private val getActorBiographyUseCase: GetActorBiographyUseCase
) : ViewModel() {

    private val _actorDetails = MutableStateFlow(DetailsState(isIdle = true))
    val actorDetails: StateFlow<DetailsState> = _actorDetails.asStateFlow()

    fun resetStateToHome() {
        _actorDetails.value = DetailsState(isIdle = true)
    }

    fun loadDetails(actorId: String){
        val currentState = _actorDetails.value
        if(!currentState.isFirstLoading)
        viewModelScope.launch {
            getActorDetailsUseCase(actorId)
                .onStart {
                    _actorDetails.update { current ->
                        current.copy(
                            isIdle = false,
                            isFirstLoading = true,
                            details = null,
                            errorMessage = null
                        )
                    }
                }
                .collect { result: Result<ActorModel> ->
                    result.onSuccess {actorModel: ActorModel ->
                        _actorDetails.update { current ->
                            current.copy(
                                isIdle = false,
                                isFirstLoading = false,
                                details = actorModel,
                                errorMessage = null
                            )
                        }
                        getActorBiographyUseCase(actorId.toInt(), actorModel.biography)
                    }
                    result.onFailure {
                        _actorDetails.update { current ->
                            current.copy(
                                isIdle = false,
                                isFirstLoading = false,
                                details = null,
                                errorMessage = "Error loading actor details"
                            )
                        }
                    }
                }
        }
    }
}

data class DetailsState(
    val isIdle: Boolean = true,
    val details: ActorModel? = null,
    val isFirstLoading: Boolean = false,
    val errorMessage: String? = null
)