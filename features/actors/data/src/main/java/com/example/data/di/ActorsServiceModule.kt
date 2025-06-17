package com.example.data.di

import com.example.data.services.ActorsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ActorsServiceModule {
    @Provides
    fun provideActorsService(retrofit: Retrofit): ActorsService {
        return retrofit.create(ActorsService::class.java)
    }
}