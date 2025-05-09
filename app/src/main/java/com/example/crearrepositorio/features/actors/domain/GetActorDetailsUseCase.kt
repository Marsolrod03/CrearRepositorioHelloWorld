package com.example.crearrepositorio.features.actors.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    operator fun invoke(actorId: String): Flow<Result<ActorModel>> =
        actorsRepository.getActorDetails(actorId)
}