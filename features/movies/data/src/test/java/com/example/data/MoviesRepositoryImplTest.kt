package com.example.data

import com.example.data.dataSource.MoviesLocalDataSource
import com.example.data.dataSource.MoviesNetworkDataSource
import com.example.data.database.entities.MovieEntity
import com.example.data.dto.MovieDTO
import com.example.data.dto.MoviePageDTO
import com.example.data.implementation.MovieRepositoryImpl
import com.example.domain.model.MovieModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MoviesRepositoryImplTest {

    lateinit var moviesNetworkDataSource: MoviesNetworkDataSource
    lateinit var moviesLocalDataSource: MoviesLocalDataSource
    private lateinit var movieRepository: MovieRepositoryImpl

    @BeforeEach
    fun onBefore() {
        moviesNetworkDataSource = mockk(relaxed = true)
        moviesLocalDataSource = mockk(relaxed = true)
        movieRepository = MovieRepositoryImpl(moviesNetworkDataSource, moviesLocalDataSource)
    }


    @Test
    fun `WHEN clearAndUpdateDatabase() is called THEN clearDatabase(), deleteAllMoviePages(), insertPagination() and updateLastDeleteDB() are invoked`() =
        runTest {
            // GIVEN
            val timeRN = System.currentTimeMillis()

            coEvery { moviesLocalDataSource.clearDatabase() } returns Unit
            coEvery { moviesLocalDataSource.deleteAllMoviePages() } returns Unit
            coEvery { moviesLocalDataSource.updateLastDeleteDB(timeRN) } returns Unit
            coEvery { moviesLocalDataSource.insertPagination() } returns Unit

            // WHEN
            movieRepository.clearAndUpdateDatabase(timeRN)

            // THEN
            coVerify(exactly = 1) {
                moviesLocalDataSource.clearDatabase()
                moviesLocalDataSource.deleteAllMoviePages()
                moviesLocalDataSource.updateLastDeleteDB(timeRN)
                moviesLocalDataSource.insertPagination()
            }
            confirmVerified(moviesLocalDataSource)
        }

    @Test
    fun `WHEN accumulatedMovies is empty and databaseMovies is not empty THEN emit accumulatedMovies`() =
        runTest {
            //GIVEN
            val movieEntity = MovieEntity(
                id = "1",
                title = "test",
                overview = "sample",
                poster_path = "",
                backdrop_path = "",
                popularity = 1.1,
                release_date = "",
                vote_average = 1.1
            )
            val lastMovie = 1
            val totalPages = 2
            val databaseMovies = listOf(movieEntity)

            coEvery { moviesLocalDataSource.getAllMovies() } returns databaseMovies
            coEvery { moviesLocalDataSource.getLastMoviePage() } returns lastMovie
            coEvery { moviesLocalDataSource.getTotalPages() } returns totalPages

            // WHEN
            movieRepository.accumulatedMovies.clear()
            val result = movieRepository.manageMoviesPagination().toList()

            // THEN
            assertEquals(true, result.first().isSuccess)

            coVerify(exactly = 1) {
                moviesLocalDataSource.getAllMovies()
                moviesLocalDataSource.getTotalPages()
                moviesLocalDataSource.getLastMoviePage()
            }
            confirmVerified(moviesLocalDataSource)
        }

    @Test
    fun `WHEN accumulatedMovies and databaseMovies are empty THEN fetch from api`() =
        runTest {
            // GIVEN
            coEvery { moviesLocalDataSource.getAllMovies() } returns emptyList()
            coEvery { moviesLocalDataSource.getLastMoviePage() } returns 0
            //TODO PONER 0 ESTUDIAR.
            coEvery { moviesLocalDataSource.getTotalPages() } returns 2

            val moviePageDTO = MoviePageDTO(
                page = 1,
                total_pages = 2,
                results = listOf(
                    MovieDTO(
                        id = "1",
                        title = "Test",
                        overview = "Test",
                        poster_path = "",
                        backdrop_path = "",
                        popularity = 1.1,
                        release_date = "",
                        vote_average = 1.1
                    )
                ),
                total_results = 900
            )

            coEvery { moviesNetworkDataSource.fetchPopularMovies(any()) } returns moviePageDTO
            coEvery { moviesLocalDataSource.insertAllMovies(any()) } returns Unit
            coEvery { moviesLocalDataSource.updateTotalPages(any()) } returns Unit
            coEvery { moviesLocalDataSource.updateLastLoadedPage(any()) } returns Unit
            coEvery { moviesLocalDataSource.insertPagination() } returns Unit

            // WHEN
            val result = movieRepository.manageMoviesPagination().toList()

            // THEN
            val wrapperResult = result.first().getOrNull()
            assertEquals(1, wrapperResult?.movieList?.size)
            assertEquals("Test", wrapperResult?.movieList?.first()?.title)

            coVerify(exactly = 1) {
                moviesLocalDataSource.insertAllMovies(any())
                moviesLocalDataSource.updateTotalPages(2)
                moviesLocalDataSource.insertPagination()
                moviesLocalDataSource.updateLastLoadedPage(1)
                moviesLocalDataSource.getAllMovies()
                moviesLocalDataSource.getLastMoviePage()
                moviesLocalDataSource.getTotalPages()
                moviesNetworkDataSource.fetchPopularMovies(any())

            }
            confirmVerified(moviesLocalDataSource, moviesNetworkDataSource)
        }

    @Test
    fun `WHEN api fails THEN emit database movies as fallback`() = runTest {
        // Given
        coEvery { moviesLocalDataSource.getAllMovies() } returns emptyList()
        coEvery { moviesLocalDataSource.getLastMoviePage() } returns 0
        coEvery { moviesLocalDataSource.getTotalPages() } returns 2

        coEvery { moviesNetworkDataSource.fetchPopularMovies(any()) } returns null

        // When
        val result = movieRepository.manageMoviesPagination().toList()

        // Then
        val wrapper = result.first().getOrNull()
        assertEquals(emptyList<MovieModel>(), wrapper?.movieList)
        assertEquals(Int.MAX_VALUE, wrapper?.totalPages)
        assertEquals(true, result.first().isSuccess)

        coVerify {
            moviesLocalDataSource.getLastMoviePage()
            moviesLocalDataSource.getAllMovies()
            moviesLocalDataSource.getTotalPages()
            moviesLocalDataSource.insertAllMovies(emptyList())
            moviesLocalDataSource.updateTotalPages(Int.MAX_VALUE)
            moviesLocalDataSource.insertPagination()
            moviesLocalDataSource.updateLastLoadedPage(1)
            moviesNetworkDataSource.fetchPopularMovies(any())
        }

        confirmVerified(moviesLocalDataSource, moviesNetworkDataSource)
    }

    @Test
    fun `WHEN movie detail is fetched successfully from api THEN emit movie model`() =
        runTest {
            val movieId = 1
            val movieDTO = MovieDTO(
                id = "1", title = "test", overview = "test",
                poster_path = "", backdrop_path = "",
                popularity = 1.0, release_date = "", vote_average = 1.0
            )
            coEvery { moviesNetworkDataSource.fetchDetailMovies(movieId) } returns movieDTO

            val result = movieRepository.manageMovieDetails(movieId).toList()

            assertEquals(true, result.first().isSuccess)
            assertEquals("test", result.first().getOrNull()?.title)
        }

    @Test
    fun `WHEN api fails to fetch movie detail THEN fallback to local database`() = runTest {
        val movieId = 1
        val movieEntity = MovieEntity(
            id = "1", title = "test", overview = "test",
            poster_path = "", backdrop_path = "",
            popularity = 1.0, release_date = "", vote_average = 1.0
        )

        coEvery { moviesNetworkDataSource.fetchDetailMovies(movieId) } returns null
        coEvery { moviesLocalDataSource.getMovieDetails(movieId) } returns movieEntity

        val result = movieRepository.manageMovieDetails(movieId).toList()

        assertEquals(true, result.first().isSuccess)
        assertEquals("test", result.first().getOrNull()?.title)
    }


}
