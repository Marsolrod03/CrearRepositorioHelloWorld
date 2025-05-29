package com.example.movieDatabase.features.series.data.di

import com.example.movieDatabase.features.series.data.SeriesRepositoryImpl
import com.example.movieDatabase.features.series.domain.SeriesRepository
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