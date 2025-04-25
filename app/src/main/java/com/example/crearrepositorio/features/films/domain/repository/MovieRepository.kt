package com.example.crearrepositorio.features.films.domain.repository

import com.example.crearrepositorio.features.films.data.implementation.MovieRepositoryImpl
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun createMovies(): Flow<List<MovieModel>>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {
    
    @Binds
    abstract fun bindMoviesRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository
}