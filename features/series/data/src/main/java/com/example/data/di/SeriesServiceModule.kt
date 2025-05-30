package com.example.data.di

import com.example.data.network.SeriesService
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
