package com.example.domain.use_cases

import com.example.domain.TimeProvider
import com.example.domain.repositories.ActorsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Calendar

class DeleteDatabaseUseCaseTest {
 private lateinit var actorsRepository: ActorsRepository
 private lateinit var timeProvider: TimeProvider
 private lateinit var getDeleteDatabaseUseCase: DeleteDatabaseUseCase

 @BeforeEach
 fun setUp() {
  actorsRepository = mockk(relaxed = true)
  timeProvider = mockk(relaxed = true)

  getDeleteDatabaseUseCase = DeleteDatabaseUseCase(actorsRepository, timeProvider)
 }

 @Test
 fun `GIVEN lastDeletion before monday 8am WHEN getDeleteDatabaseUseCase THEN clear the database`() = runTest {
  val now = Calendar.getInstance().apply {
   set(Calendar.YEAR, 2025)
   set(Calendar.MONTH, Calendar.JUNE)
   set(Calendar.DAY_OF_MONTH, 3)
   set(Calendar.HOUR_OF_DAY, 10)
   set(Calendar.MINUTE, 0)
   set(Calendar.SECOND, 0)
   set(Calendar.MILLISECOND, 0)
  }

  every { timeProvider.getCurrentCalendar() } returns now
  every { timeProvider.getCurrentTimeMillis() } returns now.timeInMillis

  val lastMonday8Am = Calendar.getInstance().apply {
   timeInMillis = timeProvider.getCurrentTimeMillis()
   set(Calendar.HOUR_OF_DAY, 8)
   set(Calendar.MINUTE, 0)
   set(Calendar.SECOND, 0)
   set(Calendar.MILLISECOND, 0)
   while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
    add(Calendar.DAY_OF_WEEK, -1)
   }
  }.timeInMillis

  val simulatedOldDeletionTime = lastMonday8Am - (7 * 24 * 60 * 60 * 1000)

  coEvery { actorsRepository.getLastDeletion() } returns simulatedOldDeletionTime
  coEvery { actorsRepository.clearActors() } just runs
  coEvery { actorsRepository.clearPagination() } just runs
  coEvery { actorsRepository.insertPagination() } just runs
  coEvery { actorsRepository.updateLastDeletion(any()) } just runs

  getDeleteDatabaseUseCase.invoke()

  coVerify(exactly = 1) { actorsRepository.getLastDeletion() }
  coVerify(exactly = 1) { actorsRepository.clearActors() }
  coVerify(exactly = 1) { actorsRepository.clearPagination() }
  coVerify(exactly = 1) { actorsRepository.insertPagination() }
  coVerify(exactly = 1) { timeProvider.getCurrentCalendar() }
  coVerify(exactly = 1) { timeProvider.getCurrentTimeMillis() }

  val timestampSlot = slot<Long>()
  coVerify(exactly = 1) {
   actorsRepository.updateLastDeletion(capture(timestampSlot))
  }
  confirmVerified(actorsRepository, timeProvider)
 }

 @Test
 fun `GIVEN lastDeletion after last monday WHEN getDeleteDatabaseUseCase THEN nothing`() = runTest {
  val now = Calendar.getInstance().apply {
   set(Calendar.YEAR, 2025)
   set(Calendar.MONTH, Calendar.JUNE)
   set(Calendar.DAY_OF_MONTH, 3)
   set(Calendar.HOUR_OF_DAY, 10)
   set(Calendar.MINUTE, 0)
   set(Calendar.SECOND, 0)
   set(Calendar.MILLISECOND, 0)
  }

  every { timeProvider.getCurrentCalendar() } returns now
  every { timeProvider.getCurrentTimeMillis() } returns now.timeInMillis

  val lastMonday8Am = Calendar.getInstance().apply {
   timeInMillis = now.timeInMillis
   set(Calendar.HOUR_OF_DAY, 8)
   set(Calendar.MINUTE, 0)
   set(Calendar.SECOND, 0)
   set(Calendar.MILLISECOND, 0)
   while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
    add(Calendar.DAY_OF_WEEK, -1)
   }
  }.timeInMillis

  coEvery { actorsRepository.getLastDeletion() } returns lastMonday8Am

  getDeleteDatabaseUseCase.invoke()

  coVerify(exactly = 1) { actorsRepository.getLastDeletion() }
  coVerify(exactly = 1) { timeProvider.getCurrentCalendar() }
  coVerify(exactly = 0) { actorsRepository.clearActors() }
  coVerify(exactly = 0) { actorsRepository.clearPagination() }
  coVerify(exactly = 0) { actorsRepository.insertPagination() }
  coVerify(exactly = 0) { actorsRepository.updateLastDeletion(any()) }
  coVerify(exactly = 0) { timeProvider.getCurrentTimeMillis() }

  confirmVerified(actorsRepository, timeProvider)
 }
}