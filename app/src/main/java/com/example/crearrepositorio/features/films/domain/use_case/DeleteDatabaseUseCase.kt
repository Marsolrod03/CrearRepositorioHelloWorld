package com.example.crearrepositorio.features.films.domain.use_case

import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import java.util.Calendar
import javax.inject.Inject

class DeleteDatabaseUseCase @Inject constructor(
    private val movieRepositoryImpl: MovieRepository
) {
    suspend operator fun invoke() {
        val databaseLastDelete: Long = movieRepositoryImpl.getLastDelete()
        val timeRN = Calendar.getInstance().timeInMillis
        val lastMonday8am = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        if (databaseLastDelete < lastMonday8am) {
            movieRepositoryImpl.clearDatabase(timeRN)
        }
    }
}