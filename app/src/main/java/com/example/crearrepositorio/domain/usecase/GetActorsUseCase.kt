package com.example.crearrepositorio.domain.usecase

import com.example.crearrepositorio.data.ActorsRepositoryImpl
import com.example.crearrepositorio.domain.models.ActorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetActorsUseCase (){
    private val actorsRepository = ActorsRepositoryImpl()

    operator fun invoke(): Flow<List<ActorModel>> = flow {
        emit(actorsRepository.getActors())
    }
}