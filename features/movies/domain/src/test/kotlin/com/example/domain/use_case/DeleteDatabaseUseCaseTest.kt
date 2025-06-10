package com.example.domain.use_case

import com.example.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import java.util.Calendar
import kotlin.test.Test

class DeleteDatabaseUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var deleteDatabaseUseCase: DeleteDatabaseUseCase

    @BeforeEach
    fun setUp() {
        movieRepository = mockk(relaxed = true)
        deleteDatabaseUseCase = DeleteDatabaseUseCase(movieRepository)
    }

    @Test
    fun `WHEN last delete is before last Monday 8AM THEN clear and update database`() = runTest {
        // GIVEN
        val oldDeleteTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 7)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        coEvery { movieRepository.getLastDelete() } returns oldDeleteTime

        // WHEN
        deleteDatabaseUseCase.invoke()

        // THEN
        coVerify(exactly = 1) { movieRepository.clearAndUpdateDatabase(any()) }
    }

    @Test
    fun `WHEN last delete is after last Monday 8AM THEN do nothing`() = runTest {
        // GIVEN
        val recentDeleteTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }
        }.timeInMillis

        coEvery { movieRepository.getLastDelete() } returns recentDeleteTime

        // WHEN
        deleteDatabaseUseCase.invoke()

        // THEN
        coVerify(exactly = 0) { movieRepository.clearAndUpdateDatabase(any()) }
    }
}