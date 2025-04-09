package com.example.crearrepositorio.features.actors.domain

import com.example.crearrepositorio.features.actors.data.ActorsRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActorsUseCase (private val actorsRepository: ActorsRepository = ActorsRepositoryImpl()){

    operator fun invoke(): Flow<List<ActorModel>> = flow {
        emit(actorsRepository.getActors())
    }

}
