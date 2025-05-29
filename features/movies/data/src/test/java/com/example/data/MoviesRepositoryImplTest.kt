package com.example.data

import com.example.data.dataSource.MoviesLocalDataSource
import com.example.data.dataSource.MoviesNetworkDataSource
import com.example.data.implementation.MovieRepositoryImpl
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MoviesRepositoryImplTest {

    @RelaxedMockK
    lateinit var moviesNetworkDataSource: MoviesNetworkDataSource

    @RelaxedMockK
    lateinit var moviesLocalDataSource: MoviesLocalDataSource

    private lateinit var movieRepository: MovieRepositoryImpl

    @BeforeEach
    fun onBefore(){
        moviesNetworkDataSource = mockk()
        moviesLocalDataSource = mockk()
        movieRepository = MovieRepositoryImpl(moviesNetworkDataSource, moviesLocalDataSource)
    }

    @Test
    fun `when `(){

    }
}