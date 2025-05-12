package com.example.crearrepositorio.features.actors.data.di

import com.example.crearrepositorio.features.actors.data.services.ActorsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ActorsServiceModule {
    @Provides
    fun provideSeriesService(retrofit: Retrofit): ActorsService {
        return retrofit.create(ActorsService::class.java)
    }
}