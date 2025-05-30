package com.example.data

import com.example.data.data_source.ActorsLocalDataSource
import com.example.data.data_source.ActorsNetworkDataSource
import com.example.data.database.entities.ActorEntity
import com.example.data.dto.ActorDTO
import com.example.data.dto.PagedResultDTO
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
            val expectedResult: Result<ActorWrapper> =  Result.success(ActorWrapper(
                hasMorePages = true,
                actorsList = actorsDataBase,
                totalPages = 5
            ))

            coEvery { actorsRepository.getPaginationActors() } returns lastPage
            coEvery { actorsRepository.getActorsFromDatabase() } returns actorsDataBase
            coEvery { actorsRepository.getTotalPages() } returns totalPagesDataBase
//            coEvery { actorsRepository.getPagedActors() } returns flow {expectedResult}

            val emittedResults = mutableListOf<Result<ActorWrapper>>()
            actorsRepository.getPagedActors().toList(emittedResults)
            val result = emittedResults.first()

            assertEquals(expectedResult, result)

            coVerify(exactly = 1) { actorsRepository.getPaginationActors() }
            coVerify(exactly = 1) { actorsRepository.getActorsFromDatabase() }
            coVerify(exactly = 1) { actorsRepository.getTotalPages() }
            coVerify(exactly = 1) { actorsRepository.getPagedActors() }
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
//        val resultFlow = actorsRepository.getPagedActors()

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

    @Test
    fun `WHEN insertActors THEN it should map ActorModels to ActorEntities and insert them`() = runTest {
        val actorModel1 = ActorModel(
            id = 1,
            name = "Name",
            image = "",
            popularity = 7.5,
            gender = Gender.Male,
            biography = "Info"
        )
        val actorsToInsert = listOf(actorModel1)
        val expectedActorEntities = listOf(actorModel1.toActorEntity())

        actorsRepository.insertActors(actorsToInsert)

        coVerify(exactly = 1) { actorsLocalDataSource.insertAllActors(expectedActorEntities) }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN clearActors THEN should call to deleteAllActors from local data source`() = runTest {
        actorsRepository.clearActors()

        coVerify(exactly = 1) { actorsLocalDataSource.deleteAllActors() }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN updateActorBiography THEN should call to updateActorBiography from local data source`() = runTest {
        val actorIdToUpdate = 1
        val newBiography = "Updated Biography"

        actorsRepository.updateActorBiography(actorIdToUpdate, newBiography)

        coVerify(exactly = 1) { actorsLocalDataSource.updateActorBiography(actorIdToUpdate, newBiography) }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN getPaginationActors THEN should take the value of the pagination`() = runTest {
        val expectedPaginationValue = 42
        coEvery { actorsLocalDataSource.getPaginationActors() } returns expectedPaginationValue

        val resultPagination = actorsRepository.getPaginationActors()

        coVerify(exactly = 1) { actorsLocalDataSource.getPaginationActors() }
        assertEquals(expectedPaginationValue, resultPagination)
    }

    @Test
    fun `WHEN clearPagination THEN should call deletePaginationActors on the local data source`() = runTest {
        actorsRepository.clearPagination()

        coVerify(exactly = 1) { actorsLocalDataSource.deletePaginationActors() }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN updateLastPage THEN should call updateLastPage from the local data source`() = runTest {
        val newPageNumber = 5

        actorsRepository.updateLastPage(newPageNumber)

        coVerify(exactly = 1) { actorsLocalDataSource.updateLastPage(newPageNumber) }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN insertPagination THEN should call insertPagination from the local data source`() = runTest {
        actorsRepository.insertPagination()

        coVerify(exactly = 1) { actorsLocalDataSource.insertPagination() }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN getLastDeletion THEN should return the last deletion from the local data source`() = runTest {
        val expectedTime = System.currentTimeMillis() - 100000L

        coEvery { actorsLocalDataSource.getLastDeletion() } returns expectedTime

        val resultTime = actorsRepository.getLastDeletion()

        coVerify(exactly = 1) { actorsLocalDataSource.getLastDeletion() }
        assertEquals(expectedTime, resultTime)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN updateLastDeletion THEN should call updateLastDeletion from the local data source`() = runTest {
        val timeToUpdate = System.currentTimeMillis()

        actorsRepository.updateLastDeletion(timeToUpdate)

        coVerify(exactly = 1) { actorsLocalDataSource.updateLastDeletion(timeToUpdate) }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN getTotalPages THEN should return the total pages from the local data source`() = runTest {
        val expectedTotalPages = 150

        coEvery { actorsLocalDataSource.getTotalPages() } returns expectedTotalPages

        val resultTotalPages = actorsRepository.getTotalPages()

        coVerify(exactly = 1) { actorsLocalDataSource.getTotalPages() }
        assertEquals(expectedTotalPages, resultTotalPages)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `WHEN updateTotalPages THEN should call updateTotalPages from the local data source`() = runTest {
        val newTotalPages = 200

        actorsRepository.updateTotalPages(newTotalPages)

        coVerify(exactly = 1) { actorsLocalDataSource.updateTotalPages(newTotalPages) }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN actor from api WHEN getActorDetailsApi THEN should emit success with ActorModel`() = runTest {
        val actorId = "123"
        val actorModel = ActorModel(
            id = 1,
            name = "name",
            image = "",
            popularity = 8.0,
            gender = Gender.Male,
            biography = "Info"
        )
        val expectedResult = Result.success(actorModel)
        coEvery { actorsNetworkDataSource.fetchDetails(actorId) } returns actorModel

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetailsApi(actorId).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchDetails(actorId) }
        assertEquals(expectedResult, result)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN null WHEN getActorDetailsApi THEN should emit failure with Actor details`() = runTest {
        val actorId = "456"
        coEvery { actorsNetworkDataSource.fetchDetails(actorId) } returns null

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetailsApi(actorId).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchDetails(actorId) }
        val exception = result.exceptionOrNull()
        assertEquals("Actor details not found", exception?.message)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN exception WHEN getActorDetailsApi THEN it should emit failure with that exception`() = runTest {
        val actorId = "123"
        val expectedException = RuntimeException("Network error occurred")
        coEvery { actorsNetworkDataSource.fetchDetails(actorId) } throws expectedException

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetailsApi(actorId).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchDetails(actorId) }
        val exception = result.exceptionOrNull()
        assertEquals(expectedException, exception)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN actors WHEN getPagedActorsFromApi THEN should emit success with ActorWrapper`() = runTest {
        val currentPage = 1
        val totalPages = 5
        val actorDto1 = ActorDTO(1, "name", 1, 10.0, "")
        val pagedResponseDto = PagedResultDTO(
            page = currentPage,
            results = listOf(actorDto1),
            total_pages = totalPages,
            total_results = 50
        )

        val expectedActorModels = listOf(actorDto1.toActorModel())

        val expectedActorWrapper = ActorWrapper(
            hasMorePages = true,
            actorsList = expectedActorModels,
            totalPages = totalPages
        )

        coEvery { actorsNetworkDataSource.fetchActors(currentPage) } returns pagedResponseDto

        val emittedResults = mutableListOf<Result<ActorWrapper>>()
        actorsRepository.getPagedActorsFromApi(currentPage).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchActors(currentPage) }

        assertTrue(result.isSuccess)

        val actorWrapper = result.getOrNull()
        assertEquals(expectedActorWrapper, actorWrapper)
        assertTrue(actorWrapper?.hasMorePages == true)
        assertEquals(expectedActorModels, actorWrapper?.actorsList)
        assertEquals(totalPages, actorWrapper?.totalPages)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN last page WHEN getPagedActorsFromApi THEN should emit success with ActorWrapper and no more pages`() = runTest {
        val currentPage = 5
        val totalPages = 5
        val actorDtoLast = ActorDTO(5, "Last Actor", 5, 50.0, "")
        val pagedResponseDto = PagedResultDTO(
            page = currentPage,
            results = listOf(actorDtoLast),
            total_pages = totalPages,
            total_results = 100
        )
        val expectedActorModels = listOf(actorDtoLast.toActorModel())
        val expectedActorWrapper = ActorWrapper(
            hasMorePages = false,
            actorsList = expectedActorModels,
            totalPages = totalPages
        )

        coEvery { actorsNetworkDataSource.fetchActors(currentPage) } returns pagedResponseDto

        val emittedResults = mutableListOf<Result<ActorWrapper>>()
        actorsRepository.getPagedActorsFromApi(currentPage).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchActors(currentPage) }
        assertTrue(result.isSuccess)
        val actorWrapper = result.getOrNull()
        assertEquals(expectedActorWrapper, actorWrapper)
        assertEquals(expectedActorModels, actorWrapper?.actorsList)
        assertEquals(totalPages, actorWrapper?.totalPages)
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
    }


}