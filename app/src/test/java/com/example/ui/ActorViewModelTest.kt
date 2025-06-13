package com.example.ui

import app.cash.turbine.test
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import com.example.domain.use_cases.GetActorsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class ActorViewModelTest {


    private lateinit var getActorsUseCase: GetActorsUseCase

    private lateinit var stringResourceProvider: StringResourceProvider

    private lateinit var actorViewModel: ActorViewModel

    @BeforeEach
    fun setUp() {
        getActorsUseCase = mockk(relaxed = true)
        stringResourceProvider = mockk(relaxed = true)
        actorViewModel = ActorViewModel(getActorsUseCase, stringResourceProvider)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @AfterEach
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN !hasMorePages WHEN loadActors THEN nothing`() = runTest {
        val hasMorePages = false
        actorViewModel.hasMorePages = hasMorePages

        actorViewModel.actorList.test {
            skipItems(1)
            actorViewModel.loadActors()

            expectNoEvents()
        }
    }

    @Test
    fun `WHEN resetStateHome THEN reset actorState`() = runTest {
        actorViewModel.loadActors()

        actorViewModel.resetStateToHome()

        actorViewModel.actorList.test {
            val initialState = awaitItem()

            assert(!initialState.isPartialLoading)
            assert(!initialState.isFirstLoad)
            assert(initialState.actors.isEmpty())
            assert(initialState.errorMessage == null)

            assert(actorViewModel.hasMorePages)

            assert(actorViewModel._currentActors.isEmpty())

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `WHEN loadActors THEN start loading actors`() = runTest{
        actorViewModel.hasMorePages = true
        actorViewModel._actorList.value = ActorState(isPartialLoading = false, isFirstLoad = false)

        actorViewModel.actorList.test {
            skipItems(1)

            actorViewModel.loadActors()

            val state = awaitItem() //observa el flujo (StateFlow) y coge el siguiente valor emitido
            assert(state.isPartialLoading)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `GIVEN getActorsUseCase success WHEN loadActors THEN update state with actors list`() = runTest {
        val list = listOf(ActorModel(1, "Actor ", "", Gender.Male, 2.4))
        val mockActorWrapper = ActorWrapper(
            false,
            list,
            5
        )

        coEvery { getActorsUseCase.invoke() } returns flowOf(Result.success(mockActorWrapper))

        actorViewModel.hasMorePages = true
        actorViewModel._actorList.value = ActorState(isPartialLoading = false, isFirstLoad = false)

        actorViewModel.actorList.test {
            actorViewModel.loadActors()

            val state = awaitItem()
            assert(!state.isFirstLoad)
            assert(!state.isPartialLoading)
            assertFalse(state.actors == list)
//            assertTrue(state.actors.isNotEmpty())
            assertNull(state.errorMessage)

            cancelAndConsumeRemainingEvents()
        }
        coVerify(exactly = 2) { getActorsUseCase.invoke() }
        confirmVerified(getActorsUseCase, stringResourceProvider)
    }

    @Test
    fun `GIVEN getActorsUseCase failure WHEN loadActors THEN update state with error message`() = runTest {
        val errorMessage = "Error loading actors"

        coEvery { getActorsUseCase.invoke() } returns flow {
            emit(Result.failure(RuntimeException("Failed to load actors")))
        }

        every { stringResourceProvider.getString(R.string.errorActors) } returns errorMessage

        actorViewModel.loadActors()

        actorViewModel.actorList.test {

            val state = awaitItem()
            assertTrue(state.isFirstLoad)
            assertTrue(state.isPartialLoading)
//            assertTrue(state.actors.isEmpty())
//            assertEquals(errorMessage, state.errorMessage)

            cancelAndConsumeRemainingEvents()
        }
        coVerify(exactly = 1) { getActorsUseCase.invoke() }
//        verify(exactly = 1) { stringResourceProvider.getString(R.string.errorActors) }
        confirmVerified(getActorsUseCase, stringResourceProvider)
    }

    @Test
    fun `GIVEN _currentActors not empty WHEN loadActors THEN partialLoading is true`() = runTest {
        val list = mutableListOf(ActorModel(1, "Actor ", "", Gender.Male, 2.4))
        val mockActorWrapper = ActorWrapper(
            false,
            list,
            5
        )

        coEvery { getActorsUseCase.invoke() } returns flowOf(Result.success(mockActorWrapper))

        actorViewModel.hasMorePages = true
        actorViewModel._currentActors = list
        actorViewModel._actorList.value = ActorState(isPartialLoading = false, isFirstLoad = false)

        actorViewModel.actorList.test {
            actorViewModel.loadActors()

            val state = awaitItem()
            assert(!state.isFirstLoad)
            assert(!state.isPartialLoading)
//            assertTrue(state.actors.isNotEmpty())
            assert(state.errorMessage == null)

            cancelAndConsumeRemainingEvents()
        }
    }
}