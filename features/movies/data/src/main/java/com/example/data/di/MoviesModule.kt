package com.example.data.di

import com.example.data.implementation.MovieRepositoryImpl
import com.example.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MoviesModule {

    @Binds
    abstract fun bindMoviesRepository(repository: MovieRepositoryImpl): MovieRepository
}