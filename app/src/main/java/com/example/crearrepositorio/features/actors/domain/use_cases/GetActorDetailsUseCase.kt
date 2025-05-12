package com.example.crearrepositorio.features.actors.domain.use_cases

import com.example.crearrepositorio.features.actors.domain.models.ActorModel
import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    operator fun invoke(actorId: String): Flow<Result<ActorModel>> =
        actorsRepository.getActorDetails(actorId)
}