package com.example.crearrepositorio.features.films.data.di

import com.example.crearrepositorio.features.films.data.implementation.MovieRepositoryImpl
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {
    
    @Binds
    abstract fun bindMoviesRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

}