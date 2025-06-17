package com.example.data

import com.example.data.data_source.ActorsLocalDataSource
import com.example.data.data_source.ActorsNetworkDataSource
import com.example.data.database.entities.ActorEntity
import com.example.data.dto.ActorDTO
import com.example.data.dto.PagedResultDTO
import com.example.domain.ActorWrapper
import com.example.domain.models.ActorModel
import com.example.domain.models.Gender
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.just
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull


class ActorsRepositoryImplTest {

    private lateinit var actorsNetworkDataSource: ActorsNetworkDataSource

    private lateinit var actorsLocalDataSource: ActorsLocalDataSource

    private lateinit var actorsRepository: ActorsRepositoryImpl

    @BeforeEach
    fun setUp() {
        actorsLocalDataSource = mockk(relaxed = true)
        actorsNetworkDataSource = mockk(relaxed = true)
        actorsRepository = spyk(ActorsRepositoryImpl(actorsNetworkDataSource, actorsLocalDataSource))
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

            coEvery { actorsLocalDataSource.getPaginationActors() } returns lastPage
            coEvery { actorsRepository.getActorsFromDatabase() } returns actorsDataBase
            coEvery { actorsLocalDataSource.getTotalPages() } returns totalPagesDataBase

            val emittedResults = mutableListOf<Result<ActorWrapper>>()
            actorsRepository.getPagedActors().toList(emittedResults)
            val result = emittedResults.first()

            assertEquals(expectedResult, result)

            coVerify(exactly = 1) {
                actorsRepository.getPagedActors()
                actorsLocalDataSource.getPaginationActors()
                actorsRepository.getActorsFromDatabase()
                actorsLocalDataSource.getTotalPages()
                actorsRepository.getPagedActors()
            }
            confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
        }

    @Test
    fun `GIVEN lastPageDatabase lower currentPage WHEN getPagedActors THEN should call API and update local data`() =
        runTest {
            val lastPage = 1
            val currentPage = 1
            val totalPagesDataBase = 3

            actorsRepository.currentPage = currentPage

            val apiActor = ActorModel(
                id = 1,
                name = "actor",
                image = "",
                popularity = 8.5,
                gender = Gender.Female,
                biography = ""
            )
            val apiWrapper = ActorWrapper(true, listOf(apiActor), 3)

            coEvery { actorsLocalDataSource.getPaginationActors() } returns lastPage
            coEvery { actorsRepository.getActorsFromDatabase() } returns emptyList()
            coEvery { actorsLocalDataSource.getTotalPages() } returns totalPagesDataBase
            coEvery { actorsRepository.getPagedActorsFromApi(currentPage + 1) } returns flowOf(Result.success(apiWrapper))
            coEvery { actorsLocalDataSource.insertAllActors(any()) } just Runs
            coEvery { actorsLocalDataSource.updateTotalPages(any()) } just Runs
            coEvery { actorsLocalDataSource.updateLastPage(any()) } just Runs

            val emittedResults = mutableListOf<Result<ActorWrapper>>()
            actorsRepository.getPagedActors().toList(emittedResults)
            val result = emittedResults.first()

            assertTrue(result.isSuccess)
            assertEquals(apiWrapper, result.getOrNull())
            assertEquals(currentPage + 1, actorsRepository.currentPage)

            coVerify(exactly = 1) {
                actorsLocalDataSource.getPaginationActors()
                actorsRepository.getActorsFromDatabase()
                actorsLocalDataSource.getTotalPages()
                actorsRepository.getPagedActors()
                actorsRepository.getPagedActorsFromApi(currentPage + 1)
                actorsLocalDataSource.insertAllActors(any())
                actorsLocalDataSource.updateTotalPages(any())
                actorsLocalDataSource.updateLastPage(any())
            }
            confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
        }

    @Test
    fun `GIVEN lastPage equals totalPages WHEN getPagedActors THEN should return database actors with hasMorePages false`() = runTest {
        val lastPage = 2
        val currentPage = 1
        val totalPagesDataBase = 5

        actorsRepository.currentPage = currentPage

        val dbActor = ActorModel(
            id = 1,
            name = "dbActor",
            image = "",
            popularity = 7.0,
            gender = Gender.Male,
            biography = "Info"
        )

        coEvery { actorsLocalDataSource.getPaginationActors() } returns lastPage
        coEvery { actorsRepository.getActorsFromDatabase() } returns listOf(dbActor)
        coEvery { actorsLocalDataSource.getTotalPages() } returns totalPagesDataBase

        val results = actorsRepository.getPagedActors().toList()

        assertEquals(1, results.size)
        val result = results[0].getOrNull()

        assertNotNull(result)
        assertEquals(listOf(dbActor), result.actorsList)
        assertEquals(totalPagesDataBase, result.totalPages)
        assertTrue(result.hasMorePages)

        coVerify(exactly = 1) {
            actorsLocalDataSource.getPaginationActors()
            actorsRepository.getActorsFromDatabase()
            actorsLocalDataSource.getTotalPages()
        }
        confirmVerified(actorsLocalDataSource, actorsNetworkDataSource)
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
    fun `WHEN insertActors THEN it should map ActorModels to ActorEntities and insert them`() = runTest {
        val actorModel1 = ActorModel(
            id = 1,
            name = "Name",
            image = "",
            popularity = 7.5,
            gender = Gender.Male,
            biography = "Info"
        )
        val actorEntity1 = actorModel1.toActorEntity()
        val actorsToInsert = listOf(actorEntity1)
        val expectedActorEntities = listOf(actorModel1.toActorEntity())

        actorsLocalDataSource.insertAllActors(actorsToInsert)

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
    fun `WHEN clearPagination THEN should call deletePaginationActors on the local data source`() = runTest {
        actorsRepository.clearPagination()

        coVerify(exactly = 1) { actorsLocalDataSource.deletePaginationActors() }
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

    @Test
    fun `GIVEN API throws exception WHEN getPagedActorsFromApi THEN should emit failure`() = runTest {
        val currentPage = 1
        val testException = RuntimeException("Network error occurred")

        coEvery { actorsNetworkDataSource.fetchActors(currentPage) } throws testException

        val emittedResults = mutableListOf<Result<ActorWrapper>>()
        actorsRepository.getPagedActorsFromApi(currentPage).toList(emittedResults)
        val result = emittedResults.first()

        coVerify(exactly = 1) { actorsNetworkDataSource.fetchActors(currentPage) }
        assertTrue(result.isFailure)

        assertEquals(testException.message, result.exceptionOrNull()?.message)

        confirmVerified(actorsNetworkDataSource, actorsLocalDataSource)
    }

    @Test
    fun `GIVEN local actor with biography WHEN getActorDetails THEN should emit local actor`() = runTest {
        val actorId = 123
        val localActor = ActorModel(
            id = 123,
            name = "actor",
            popularity = 9.5,
            image = "",
            gender = Gender.Female,
            biography = "biography"
        )
        val expectResult = Result.success(localActor)

        coEvery { actorsLocalDataSource.getActorById(actorId) } returns localActor

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetails(actorId.toString()).toList(emittedResults)
        val result = emittedResults.first()

        assertTrue(result.isSuccess)
        assertEquals(expectResult, result)

        coVerify(exactly = 1) { actorsLocalDataSource.getActorById(actorId) }
        coVerify(exactly = 0) { actorsNetworkDataSource.fetchDetails(any()) }
        confirmVerified(actorsNetworkDataSource, actorsLocalDataSource)
    }

    @Test
    fun `GIVEN local actor with without biography WHEN getActorDetails THEN should emit API actor`() = runTest {
        val actorId = "456"
        val localActor = ActorModel(
            id = 456,
            name = "name",
            popularity = 5.0,
            image = "",
            biography = "Info",
            gender = Gender.Male
        )
        val apiActorDto = ActorDTO(
            id = 456,
            name = "name",
            popularity = 5.0,
            profile_path = "",
            gender = 2
        )
        val expectedApiActor = apiActorDto.toActorModel()
        val expectResult = Result.success(expectedApiActor)

        coEvery { actorsLocalDataSource.getActorById(actorId.toInt()) } returns localActor
        coEvery { actorsNetworkDataSource.fetchDetails(actorId) } returns expectedApiActor

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetails(actorId).toList(emittedResults)
        val result = emittedResults.first()

        assertTrue(result.isSuccess)
        assertEquals(expectResult, result)

        coVerify(exactly = 1) {
            actorsRepository.getActorDetails(actorId)
            actorsRepository.getActorDetailsApi(actorId)
            actorsLocalDataSource.getActorById(actorId.toInt())
            actorsNetworkDataSource.fetchDetails(actorId)
        }
        confirmVerified(actorsRepository, actorsNetworkDataSource)
    }

    @Test
    fun `GIVEN local actor without biography and api fails WHEN getActorDetails THEN should emit local actor`() = runTest {
        val actorId = "789"
        val localActor = ActorModel(
            id = 789,
            name = "name",
            popularity = 5.0,
            image = "",
            biography = "Info",
            gender = Gender.Male
        )
        val testException = RuntimeException("API network error")
        val expectResult = Result.success(localActor)

        coEvery { actorsLocalDataSource.getActorById(actorId.toInt()) } returns localActor
        coEvery { actorsNetworkDataSource.fetchDetails(actorId) } throws testException

        val emittedResults = mutableListOf<Result<ActorModel>>()
        actorsRepository.getActorDetails(actorId).toList(emittedResults)
        val result = emittedResults.first()

        assertTrue(result.isSuccess)
        assertEquals(expectResult, result)

        coVerify(exactly = 1) {
            actorsRepository.getActorDetails(actorId)
            actorsRepository.getActorDetailsApi(actorId)
            actorsLocalDataSource.getActorById(actorId.toInt())
            actorsNetworkDataSource.fetchDetails(actorId)
        }

        confirmVerified(actorsRepository, actorsNetworkDataSource)
    }
}