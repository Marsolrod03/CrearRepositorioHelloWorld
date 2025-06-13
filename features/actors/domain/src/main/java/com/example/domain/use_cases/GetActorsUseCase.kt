package com.example.domain.use_cases

import com.example.domain.ActorWrapper
import com.example.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    fun invoke(): Flow<Result<ActorWrapper>> = actorsRepository.getPagedActors()
}




