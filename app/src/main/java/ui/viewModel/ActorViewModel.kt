package ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.ActorsRepositoryImpl
import domain.models.ActorModel
import domain.usecase.GetActorsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActorViewModel : ViewModel(){
    private val _actorList = MutableStateFlow<ActorState>(ActorState.Idle)
    val actorList: StateFlow<ActorState> = _actorList.asStateFlow()
    private val actorsRepositoryImpl = ActorsRepositoryImpl()
    private val getActorsUseCase = GetActorsUseCase(actorsRepositoryImpl)

    init {
        loadActors()
    }

    private fun loadActors(){
        viewModelScope.launch {
            getActorsUseCase().collect { list ->
                _actorList.update { ActorState.Success(list) }
            }
        }
    }
}

sealed class ActorState{
    data object Idle: ActorState()
    data class Success(val actors: List<ActorModel>) : ActorState()
}


