package com.example.crearrepositorio.features.actors.domain.use_cases

import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import javax.inject.Inject

class GetActorBiographyUseCase@Inject constructor(private val actorsRepository: ActorsRepository) {
    suspend operator fun invoke(id: Int, biography: String){
        actorsRepository.updateActorBiography(id, biography)
    }
}