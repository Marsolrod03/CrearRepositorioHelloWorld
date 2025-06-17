package com.example.domain.use_cases

import com.example.domain.TimeProvider
import com.example.domain.repositories.ActorsRepository
import java.util.Calendar
import javax.inject.Inject

class ActorsDeleteDatabaseUseCase @Inject constructor(
    private val actorsRepository: ActorsRepository,
    private val timeProvider: TimeProvider
) {
    suspend operator fun invoke() {
        val nowCalendar = timeProvider.getCurrentCalendar()

        val lastMonday8AmMillis = nowCalendar.apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        val lastDeletionFromRepo = actorsRepository.getLastDeletion()

        if (lastDeletionFromRepo < lastMonday8AmMillis) {
            actorsRepository.clearActors()
            actorsRepository.clearPagination()
            actorsRepository.insertPagination()
            actorsRepository.updateLastDeletion(timeProvider.getCurrentTimeMillis())
        }
    }
}