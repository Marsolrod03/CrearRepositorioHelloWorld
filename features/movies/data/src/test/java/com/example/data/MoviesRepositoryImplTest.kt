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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MoviesRepositoryImplTest {

    @RelaxedMockK
    lateinit var moviesNetworkDataSource: MoviesNetworkDataSource

    @RelaxedMockK
    lateinit var moviesLocalDataSource: MoviesLocalDataSource

    private lateinit var movieRepository: MovieRepositoryImpl

    @BeforeEach
    fun onBefore() {
        moviesNetworkDataSource = mockk()
        moviesLocalDataSource = mockk()
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
}