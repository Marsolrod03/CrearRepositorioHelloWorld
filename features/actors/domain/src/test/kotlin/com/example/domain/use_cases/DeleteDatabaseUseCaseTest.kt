package com.example.domain.use_cases

import com.example.domain.repositories.ActorsRepository
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeleteDatabaseUseCaseTest {


 private lateinit var actorsRepository: ActorsRepository

 private lateinit var getActorDetailsUseCase: GetActorDetailsUseCase

 @BeforeEach
 fun setUp() {
  actorsRepository = mockk()
  getActorDetailsUseCase = GetActorDetailsUseCase(actorsRepository)
 }

 @Test
 fun `WHEN getActorDetailsUseCase THEN return success`() = runTest {

 }
}