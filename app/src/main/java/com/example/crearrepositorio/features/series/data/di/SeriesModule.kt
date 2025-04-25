package com.example.crearrepositorio.features.series.data.di

import com.example.crearrepositorio.features.series.data.SeriesRepositoryImpl
import com.example.crearrepositorio.features.series.domain.SeriesRepository
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