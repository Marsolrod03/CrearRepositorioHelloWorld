package com.example.movieDatabase.features.actors.data.di

import com.example.movieDatabase.features.actors.data.ActorsRepositoryImpl
import com.example.movieDatabase.features.actors.domain.ActorsRepository
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
}