package com.example.crearrepositorio.features.actors.domain.use_cases

import com.example.crearrepositorio.features.actors.domain.repositories.ActorsRepository
import java.util.Calendar
import javax.inject.Inject

class DeleteDatabaseUseCase @Inject constructor(private val actorsRepository: ActorsRepository) {
    suspend operator fun invoke() {
        val lastMonday8Am = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        val databaseLastDeletion: Long = actorsRepository.getLastDeletion()
        val now = Calendar.getInstance().timeInMillis

        if (databaseLastDeletion < lastMonday8Am) {
            actorsRepository.clearActors()
            actorsRepository.insertPagination()
            actorsRepository.updateLastDeletion(now)
        }
    }
}