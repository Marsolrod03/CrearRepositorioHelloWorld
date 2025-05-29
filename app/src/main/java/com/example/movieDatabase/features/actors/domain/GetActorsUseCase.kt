package com.example.movieDatabase.features.actors.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {

    operator fun invoke(): Flow<Result<ActorWrapper>> =
        actorsRepository.getPagedActors()
}


