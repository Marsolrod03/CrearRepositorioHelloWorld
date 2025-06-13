package com.example.ui.details

import app.cash.turbine.test
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import com.example.domain.use_cases.GetActorBiographyUseCase
import com.example.domain.use_cases.GetActorDetailsUseCase
import com.example.ui.R
import com.example.ui.StringResourceProvider
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ActorDetailsViewModelTest {

    private lateinit var getActorDetailsUseCase: GetActorDetailsUseCase

    private lateinit var getActorBiographyUseCase: GetActorBiographyUseCase

    private lateinit var stringResourceProvider: StringResourceProvider

    private lateinit var actorDetailsViewModel: ActorDetailsViewModel

    @BeforeEach
    fun setUp() {
        getActorDetailsUseCase = mockk(relaxed = true)
        getActorBiographyUseCase = mockk(relaxed = true)
        stringResourceProvider = mockk(relaxed = true)
        actorDetailsViewModel = ActorDetailsViewModel(
            getActorDetailsUseCase,
            getActorBiographyUseCase,
            stringResourceProvider
        )
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterEach
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN success WHEN loadDetails THEN should emit loading and success state`() = runTest {
        val actorId = "1"
        val actorModel = ActorModel(1, "name", "", Gender.Male, 10.3)
        coEvery { getActorDetailsUseCase(actorId) } returns flow {
            emit(Result.success(actorModel))
        }

        actorDetailsViewModel.loadDetails(actorId)

        actorDetailsViewModel.actorDetails.test {
            awaitItem().apply {
                assertFalse(isFirstLoading)
                assertEquals(actorModel, details)
                assertNull(errorMessage)
            }

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 1) { getActorBiographyUseCase(actorId.toInt(), actorModel.biography) }
        coVerify(exactly = 1) { getActorDetailsUseCase(actorId) }
        confirmVerified(getActorBiographyUseCase, getActorDetailsUseCase)
    }

    @Test
    fun `GIVEN failure WHEN loadDetails THEN should emit loading and failure state`() = runTest {
        val actorId = "1"
        val exception = RuntimeException("Network error")
        val errorMessage = "Error al obtener detalles del actor"
        coEvery { getActorDetailsUseCase(actorId) } returns flow {
            emit(Result.failure(exception))
        }
        every { stringResourceProvider.getString(R.string.errorActorDetails) } returns errorMessage

        actorDetailsViewModel.loadDetails(actorId)

        actorDetailsViewModel.actorDetails.test {

            awaitItem().apply {
                assertFalse(isFirstLoading)
                assertNull(details)
                assertEquals(errorMessage, this.errorMessage)
            }

            cancelAndIgnoreRemainingEvents()
        }

        coVerify(exactly = 0) { getActorBiographyUseCase(any(), any()) }
        coVerify(exactly = 1) { getActorDetailsUseCase(actorId) }
        coVerify(exactly = 1) { stringResourceProvider.getString(R.string.errorActorDetails) }
        confirmVerified(getActorBiographyUseCase, getActorDetailsUseCase, stringResourceProvider)
    }

    @Test
    fun `WHEN resetStateToHome THEN should reset state to default`() = runTest {
        actorDetailsViewModel.actorDetails

        actorDetailsViewModel.resetStateToHome()

        actorDetailsViewModel.actorDetails.test {
            val initialState = awaitItem()

            assertFalse(initialState.isFirstLoading)
            assertNull(initialState.details)
            assertNull(initialState.errorMessage)


            cancelAndConsumeRemainingEvents()
        }
    }
}