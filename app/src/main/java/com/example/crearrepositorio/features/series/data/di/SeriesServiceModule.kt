package com.example.crearrepositorio.features.series.data.di

import com.example.crearrepositorio.features.series.data.SeriesNetworkDataSource
import com.example.crearrepositorio.features.series.data.SeriesRepositoryImpl
import com.example.crearrepositorio.features.series.data.network.SeriesService
import com.example.crearrepositorio.features.series.domain.SeriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object SeriesServiceModule {

    @Provides
    fun provideSeriesService(retrofit: Retrofit): SeriesService {
        return retrofit.create(SeriesService::class.java)
    }
}
