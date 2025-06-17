package com.example.domain.use_cases

import com.example.domain.repositories.ActorsRepository
import javax.inject.Inject

class GetActorBiographyUseCase@Inject constructor(private val actorsRepository: ActorsRepository) {
    suspend operator fun invoke(id: Int, biography: String){
        actorsRepository.updateActorBiography(id, biography)
    }
}