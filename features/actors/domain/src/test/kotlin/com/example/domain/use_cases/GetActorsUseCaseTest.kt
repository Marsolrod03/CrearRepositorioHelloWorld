package com.example.domain.use_cases

import app.cash.turbine.test
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import com.example.domain.repositories.ActorsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetActorsUseCaseTest {


 private lateinit var actorsRepository: ActorsRepository

 private lateinit var getActorsUseCase: GetActorsUseCase

 @BeforeEach
 fun setUp() {
  actorsRepository = mockk()
  getActorsUseCase = GetActorsUseCase(actorsRepository)
 }

 @Test
 fun `WHEN getPagedActors THEN return success`() = runTest {

  val actor = ActorModel(
   id = 1,
   name = "Name  ",
   image = "",
   popularity = 1.0,
   gender = Gender.Male,
   biography = "Info"
  )
  val actorWrapper =
   ActorWrapper(hasMorePages = false, actorsList = listOf(actor), totalPages = 1000)
  val expectedResult = Result.success(actorWrapper)
  coEvery { actorsRepository.getPagedActors() } returns flowOf(expectedResult)

  // WHEN
  val resultFlow = getActorsUseCase()

  // THEN
  resultFlow.test {
   assertEquals(expectedResult, awaitItem())

   cancelAndIgnoreRemainingEvents()
  }
 }

}

