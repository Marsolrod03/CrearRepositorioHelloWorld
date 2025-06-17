package com.example.domain.use_case

import app.cash.turbine.test
import com.example.domain.MovieWrapper
import com.example.domain.model.MovieModel
import com.example.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test
import kotlin.test.assertEquals

class GetMoviesUseCaseTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var getMoviesUseCase: GetMoviesUseCase

    @BeforeEach
    fun onBefore() {
        movieRepository = mockk(relaxed = true)
        getMoviesUseCase = GetMoviesUseCase(movieRepository)
    }

    @Test
    fun `WHEN GetMoviesUseCase is called THEN return success`() =
        runTest {
            //GIVEN
            val movieModel = MovieModel(
                id = "",
                overview = "",
                poster_path = "",
                title = "",
                backdrop_path = "",
                popularity = 1.1,
                release_date = "",
                vote_average = 1.1
            )

            val movieWrapper = MovieWrapper(
                hasMorePages = false,
                movieList = listOf(movieModel),
                totalPages = 100
            )

            val expectedResult = Result.success(movieWrapper)

            coEvery { movieRepository.manageMoviesPagination() } returns flowOf(expectedResult)

            //WHEN
            val resultFlow = getMoviesUseCase()

            //THEN
            resultFlow.test{
                assertEquals(expectedResult,awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

        }

}