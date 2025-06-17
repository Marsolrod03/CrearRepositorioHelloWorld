package com.example.domain.use_case

import app.cash.turbine.test
import com.example.domain.model.MovieModel
import com.example.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class GetDetailMoviesUseCaseTest {
    private lateinit var movieRepository: MovieRepository
    private lateinit var getDetailMoviesUseCase: GetDetailMoviesUseCase

    @BeforeEach
    fun onBefore() {
        movieRepository = mockk(relaxed = true)
        getDetailMoviesUseCase = GetDetailMoviesUseCase(movieRepository)
    }

    @Test
    fun `WHEN GetDetailMoviesUseCase is called THEN return success`() =
        runTest {
            //GIVEN
            val movieModel = MovieModel(
                id = "1",
                overview = "",
                poster_path = "",
                title = "",
                backdrop_path = "",
                popularity = 1.1,
                release_date = "",
                vote_average = 1.1
            )

            val expectedResult = Result.success(movieModel)

            coEvery { movieRepository.manageMovieDetails(movieModel.id.toInt()) } returns flowOf(expectedResult)

            //WHEN
            val resultFlow = getDetailMoviesUseCase(movieModel.id.toInt())

            //THEN
            resultFlow.test{
                assertEquals(expectedResult,awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        }
}