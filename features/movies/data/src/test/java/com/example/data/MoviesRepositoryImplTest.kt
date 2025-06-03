package com.example.data

import com.example.data.dataSource.MoviesLocalDataSource
import com.example.data.dataSource.MoviesNetworkDataSource
import com.example.data.database.entities.MovieEntity
import com.example.data.implementation.MovieRepositoryImpl
import com.example.data.mapper.toMovieModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MoviesRepositoryImplTest {

    @RelaxedMockK
    lateinit var moviesNetworkDataSource: MoviesNetworkDataSource

    @RelaxedMockK
    lateinit var moviesLocalDataSource: MoviesLocalDataSource

    private lateinit var movieRepository: MovieRepositoryImpl

    //TODO Verificar que se inicializan antes de cada test.
    @BeforeEach
    fun onBefore() {
//        moviesNetworkDataSource = mockk()
//        moviesLocalDataSource = mockk()
        movieRepository = MovieRepositoryImpl(moviesNetworkDataSource, moviesLocalDataSource)
    }


    //GetAllMoviesFromDatabase
    @Test
    fun `WHEN getAllMoviesFromDatabase is called THEN return expected movieModel from database`() =
        runBlocking {
            //GIVEN
            val movieEntity = MovieEntity(
                id = "1",
                overview = "",
                poster_path = "",
                title = "",
                backdrop_path = "",
                popularity = 1.0,
                release_date = "",
                vote_average = 5.0
            )

            val expectedMovieModel = movieEntity.toMovieModel()

            coEvery { moviesLocalDataSource.getAllMovies() } returns listOf(movieEntity)

            //WHEN
            val result = movieRepository.getAllMoviesFromDatabase()

            //THEN
            assertEquals(listOf(expectedMovieModel), result)
            coVerify(exactly = 1) { moviesLocalDataSource.getAllMovies() }
            confirmVerified(moviesLocalDataSource)
        }

    //GetDetailMovieFromDatabase
    @Test
    fun `WHEN getDetailMovieFromDatabase is called THEN return expected movieModel from database by id`() =
        runTest {
            //GIVEN
            val movieid = 1
            val movieEntity = MovieEntity(
                id = "1",
                overview = "",
                poster_path = "",
                title = "",
                backdrop_path = "",
                popularity = 1.0,
                release_date = "",
                vote_average = 5.0
            )

            val expectedMovieModel = movieEntity.toMovieModel()

            coEvery { moviesLocalDataSource.getMovieDetails(movieid) } returns movieEntity

            //WHEN
            val result = movieRepository.getDetailMoviesFromDatabase(movieid)

            //THEN
            assertEquals(expectedMovieModel, result)
            coVerify(exactly = 1) { moviesLocalDataSource.getMovieDetails(movieid) }
            confirmVerified(moviesLocalDataSource)
        }

    @Test
    fun `WHEN getLastMoviePage is called THEN return expected page number from database`() =
        runTest {
            //GIVEN
            val expectedPage = 1
            coEvery { moviesLocalDataSource.getLastMoviePage() } returns expectedPage

            //WHEN
            val result = movieRepository.getLastMoviePage()

            //THEN
            assertEquals(expectedPage, result)
            coVerify(exactly = 1) { moviesLocalDataSource.getLastMoviePage() }
            confirmVerified(moviesLocalDataSource)
        }

    @Test
    fun `WHEN clearAndUpdateDatabase() is called THEN clearDatabase() ,deleteAllMoviePages() and updateLastDeleteDB() are invoked`() =
        runTest {
            // GIVEN
            val timeRN = System.currentTimeMillis()

            coEvery { moviesLocalDataSource.clearDatabase() } returns Unit
            coEvery { moviesLocalDataSource.deleteAllMoviePages() } returns Unit
            coEvery { moviesLocalDataSource.updateLastDeleteDB(timeRN) } returns Unit

            // WHEN
            movieRepository.clearAndUpdateDatabase(timeRN)

            // THEN
            //TODO unificar
            coVerify(exactly = 1) { moviesLocalDataSource.clearDatabase() }
            coVerify(exactly = 1) { moviesLocalDataSource.deleteAllMoviePages() }
            coVerify(exactly = 1) { moviesLocalDataSource.updateLastDeleteDB(timeRN) }

            confirmVerified(moviesLocalDataSource)
        }
}
