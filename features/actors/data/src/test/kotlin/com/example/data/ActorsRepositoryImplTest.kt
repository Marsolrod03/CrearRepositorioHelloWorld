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
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class ActorsRepositoryImplTest{

 @RelaxedMockK
 lateinit var actorsNetworkDataSource: ActorsNetworkDataSource
 @RelaxedMockK
 lateinit var actorsLocalDataSource: ActorsLocalDataSource

 private lateinit var actorsRepository: ActorsRepositoryImpl

 @BeforeEach
 fun onBefore(){
  actorsLocalDataSource = mockk()
  actorsNetworkDataSource = mockk()
  actorsRepository = ActorsRepositoryImpl(actorsNetworkDataSource, actorsLocalDataSource)
 }

 @Test
 fun shouldEmitFromDatabaseAndUpdateTheCurrentPage() = runTest{
  //GIVEN
  val lastPage = 2
  val currentPageInitial = 1
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

  coEvery { actorsRepository.getPaginationActors() } returns lastPage
  coEvery { actorsRepository.getActorsFromDatabase() } returns actorsDataBase
  coEvery { actorsRepository.getTotalPages() } returns totalPagesDataBase
  actorsRepository["currentPage"] = currentPageInitial

  //WHEN
  val resultFlow = actorsRepository.getPagedActors()

  //THEN

 }

 @Test
 fun shouldEmitFromApiAndUpdateTheLastPageFromDatabase() = runTest{
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
   ActorWrapper(hasMorePages = false, actorsList = listOf(actor), totalPages = 1000)
  val expectedResult = Result.success(actorWrapper)

  coEvery { actorsRepository.getPaginationActors() } returns lastPage
  coEvery { actorsRepository.getPagedActorsFromApi(currentPageInitial + 1) } returns flowOf(expectedResult)
  coEvery { actorsRepository.getTotalPages() } returns totalPagesDataBase
  actorsRepository["currentPage"] = currentPageInitial

  //WHEN
  val resultFlow = actorsRepository.getPagedActors()

  //THEN

 }

 @Test
 fun `WHEN getActorsFromDatabase THEN return expected actorModel from database`() = runTest {
  val actorEntity  = ActorEntity(2, "", "", Gender.Male, 1.0, "")
  val expectedActorModel = actorEntity.toActorModel()
  coEvery { actorsLocalDataSource.getAllActors() } returns listOf(actorEntity)

  val result = actorsRepository.getActorsFromDatabase()

  assertEquals(listOf(expectedActorModel), result)
  assertEquals(expectedActorModel.id, result[0].id)

  coVerify(exactly = 1) { actorsLocalDataSource.getAllActors() }
  confirmVerified(actorsLocalDataSource)
 }
}