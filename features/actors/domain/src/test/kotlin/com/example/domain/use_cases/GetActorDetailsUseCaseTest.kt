package com.example.domain.use_cases

import app.cash.turbine.test
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


class GetActorDetailsUseCaseTest{


  private lateinit var actorsRepository: ActorsRepository

  private lateinit var getActorDetailsUseCase: GetActorDetailsUseCase

  @BeforeEach
  fun setUp() {
   actorsRepository = mockk()
   getActorDetailsUseCase = GetActorDetailsUseCase(actorsRepository)
  }

  @Test
  fun `WHEN getActorDetailsUseCase THEN return success`() = runTest {
  val actorId = "1"
   val actor = ActorModel(
    id = 1,
    name = "Name  ",
    image = "",
    popularity = 1.0,
    gender = Gender.Male,
    biography = "Info"
   )
   val expectedResult = Result.success(actor)
   coEvery { actorsRepository.getActorDetails(actorId) } returns flowOf(expectedResult)

   // WHEN
   val resultFlow = getActorDetailsUseCase(actorId)

   // THEN
   resultFlow.test {
    assertEquals(expectedResult, awaitItem())

    cancelAndIgnoreRemainingEvents()
   }
  }

 }
