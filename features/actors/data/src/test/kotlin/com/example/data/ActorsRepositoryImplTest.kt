package com.example.data

import com.example.data.data_source.ActorsLocalDataSource
import com.example.data.data_source.ActorsNetworkDataSource
import com.example.data.database.entities.ActorEntity
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ActorsRepositoryImplTest {

    private lateinit var actorsNetworkDataSource: ActorsNetworkDataSource

    private lateinit var actorsLocalDataSource: ActorsLocalDataSource

    private lateinit var actorsRepository: ActorsRepositoryImpl

    @BeforeEach
    fun setUp() {
        actorsLocalDataSource = mockk(relaxed = true)
        actorsNetworkDataSource = mockk(relaxed = true)
        actorsRepository = ActorsRepositoryImpl(actorsNetworkDataSource, actorsLocalDataSource)
    }

    @Test
    fun `GIVEN lastPageDatabase bigger than currentPage WHEN getPagedActors THEN return ActorWrapper from database`() =
        runTest {
            val lastPage = 2
            val currentPage = 1
            val totalPagesDataBase = 5
            val actor = ActorModel(
                id = 1,
                name = "Name  ",
                image = "",
                popularity = 1.0,
                gender = Gender.Male,
                biography = "Info"
            )
            val actorsDataBase = listOf(actor)
            val expectedResult: Flow<Result<ActorWrapper>> = flow {  Result.success(ActorWrapper(
                hasMorePages = true,
                actorsList = actorsDataBase,
                totalPages = 5
            ))}

            coEvery { actorsRepository.getPaginationActors() } returns lastPage
            coEvery { actorsRepository.getActorsFromDatabase() } returns actorsDataBase
            coEvery { actorsRepository.getTotalPages() } returns totalPagesDataBase

            val resultFlow = actorsRepository.getPagedActors()
            val actualResult = resultFlow.first()

            assertEquals(expectedResult, resultFlow)

            coVerify(exactly = 1) { actorsRepository.getPaginationActors() }
            coVerify(exactly = 1) { actorsRepository.getActorsFromDatabase() }
            coVerify(exactly = 1) { actorsRepository.getTotalPages() }
            confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
        }

    @Test
    fun shouldEmitFromApiAndUpdateTheLastPageFromDatabase() = runTest {
        //GIVEN
        val lastPage = 0
        val currentPageInitial = 0
        val totalPagesDataBase = 3
        val actor = ActorModel(
            id = 1,
            name = "Name  ",
            image = "",
            popularity = 1.0,
            gender = Gender.Male,
            biography = "Info"
        )
        val actorWrapper =
            ActorWrapper(hasMorePages = true, actorsList = listOf(actor), totalPages = 1000)
        val expectedResult = Result.success(actorWrapper)

        coEvery { actorsRepository.getPaginationActors() } returns lastPage
        coEvery { actorsRepository.getPagedActorsFromApi(currentPageInitial + 1) } returns flowOf(
            expectedResult
        )
        coEvery { actorsRepository.getTotalPages() } returns totalPagesDataBase

        //WHEN
        val resultFlow = actorsRepository.getPagedActors()

        //THEN

    }

    @Test
    fun `WHEN getActorsFromDatabase THEN return expected actorModel from database`() = runTest {
        val actorEntity = ActorEntity(2, "", "", Gender.Male, 1.0, "")
        val expectedActorModel = actorEntity.toActorModel()
        coEvery { actorsLocalDataSource.getAllActors() } returns listOf(actorEntity)

        val result = actorsRepository.getActorsFromDatabase()

        assertEquals(listOf(expectedActorModel), result)
        assertEquals(expectedActorModel.id, result[0].id)

        coVerify(exactly = 1) { actorsLocalDataSource.getAllActors() }
        confirmVerified(actorsLocalDataSource)
    }

    @Test
    fun `WHEN getActorsFromDatabase THEN return emptyList`() = runTest {
        val list = emptyList<ActorEntity>()

        coEvery { actorsLocalDataSource.getAllActors() } returns list

        val result = actorsRepository.getActorsFromDatabase()

        assertEquals(list, result)

        coVerify(exactly = 1) { actorsLocalDataSource.getAllActors() }
        confirmVerified(actorsLocalDataSource)
    }

    @Test
    fun `GIVEN an id that exits WHEN getActorById THEN return an ActorModel`() = runTest {
        val idGiven = 2
        val actorExpected = ActorModel(idGiven, "", "", Gender.Male, 1.0, "")
        coEvery { actorsLocalDataSource.getActorById(idGiven) } returns actorExpected

        val result = actorsRepository.getActorsFromDatabase()

        assertEquals(actorExpected, result)

        coVerify(exactly = 1) { actorsLocalDataSource.getActorById(idGiven) }
        confirmVerified(actorsLocalDataSource)
    }
}