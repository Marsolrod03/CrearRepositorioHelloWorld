package com.example.crearrepositorio.features.actors.domain

import com.example.crearrepositorio.features.actors.data.ActorsRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetActorsUseCase (private val actorsRepository: ActorsRepository = ActorsRepositoryImpl()){

    operator fun invoke(): Flow<List<ActorModel>> = actorsRepository.getActors()

}
