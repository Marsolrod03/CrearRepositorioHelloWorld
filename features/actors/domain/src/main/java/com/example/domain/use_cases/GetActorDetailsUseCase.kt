package com.example.domain.use_cases

import com.example.domain.models.ActorModel
import com.example.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorDetailsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    suspend operator fun invoke(actorId: String): Flow<Result<ActorModel>> =
        actorsRepository.getActorDetails(actorId)
}