package com.example.crearrepositorio.features.actors.domain.use_cases

import com.example.crearrepositorio.features.actors.domain.models.ActorModel
import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    operator fun invoke(actorId: String): Flow<Result<ActorModel>> = flow {
        val localActor = actorsRepository.getActorById(actorId.toInt())
        if (localActor.biography != "Info") {
            emit(Result.success(localActor))
        } else {
            actorsRepository.getActorDetails(actorId)
                .collect { result ->
                    result.onSuccess { actorModel ->
                        emit(Result.success(actorModel))
                    }
                    result.onFailure {
                        emit(Result.success(localActor))
                    }
                }
        }
    }
}