package com.example.data.di

import com.example.data.SeriesRepositoryImpl
import com.example.domain.repository.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SeriesModule {
    @Binds
    abstract fun bindSeriesRepository(repository: SeriesRepositoryImpl): SeriesRepository

}