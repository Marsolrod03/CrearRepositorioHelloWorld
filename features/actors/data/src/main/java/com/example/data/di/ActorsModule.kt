package com.example.data.di

import com.example.data.ActorsRepositoryImpl
import com.example.data.TimeProviderImplementation
import com.example.domain.TimeProvider
import com.example.domain.repositories.ActorsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ActorsModule {

    @Singleton
    @Binds
    abstract fun bindActorsRepository(
        actorsRepositoryImpl: ActorsRepositoryImpl
    ): ActorsRepository

    @Singleton
    @Binds
    abstract fun bindTimeProvider(
        timeProviderImplementation: TimeProviderImplementation
    ): TimeProvider
}