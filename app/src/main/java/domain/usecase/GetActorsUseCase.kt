package domain.usecase

import domain.ActorsRepository
import domain.models.ActorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActorsUseCase (private val actorsRepository: ActorsRepository){
    operator fun invoke(): Flow<List<ActorModel>> = flow {
        emit(actorsRepository.getActors())
    }
}