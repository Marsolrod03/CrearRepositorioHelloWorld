package com.example.domain.use_cases

import com.example.domain.repositories.ActorsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetActorBiographyUseCaseTest {


    private lateinit var actorsRepository: ActorsRepository

    private lateinit var getActorBiographyUseCase: GetActorBiographyUseCase

    @BeforeEach
    fun setUp() {
        actorsRepository = mockk()
        getActorBiographyUseCase = GetActorBiographyUseCase(actorsRepository)
    }

    @Test
    fun `WHEN getActorBiographyUseCase THEN return success`() = runTest {
        val actorId = 123
        val biography = "Actor's biography."
        coEvery { actorsRepository.updateActorBiography(actorId, biography) } returns Unit

        getActorBiographyUseCase(actorId, biography)

        coVerify(exactly = 1) { actorsRepository.updateActorBiography(actorId, biography) }
        confirmVerified(actorsRepository)
    }

}



