package com.example.crearrepositorio.features.actors.domain.use_cases

import com.example.crearrepositorio.features.actors.domain.ActorWrapper
import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetActorsUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    private var currentPage = 1
    private var databaseLoading = false
    operator fun invoke(): Flow<Result<ActorWrapper>> = flow {
        if (actorsRepository.getActorsFromDatabase().isNotEmpty() && !databaseLoading) {
            databaseLoading = true
            emit(Result.success(ActorWrapper(true, actorsRepository.getActorsFromDatabase())))
        }else{
            if (actorsRepository.getPaginationActors() >= 1) {
                currentPage = actorsRepository.getPaginationActors()
            }
            actorsRepository.getPagedActorsFromApi(currentPage)
                .collect { result ->
                    result.onSuccess { actorWrapper ->
                        actorsRepository.insertActors(actorWrapper.actorsList)
                        currentPage++
                        if (currentPage > actorsRepository.getPaginationActors()) {
                            actorsRepository.updateLastPage(currentPage)
                        }
                        emit(Result.success(actorWrapper))
                    }
                }

        }
    }
}




