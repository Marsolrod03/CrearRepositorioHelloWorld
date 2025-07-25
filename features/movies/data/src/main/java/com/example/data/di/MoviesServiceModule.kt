package com.example.data.di

import com.example.data.network.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object MoviesServiceModule {

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService {
        return retrofit.create(MoviesService::class.java)
    }
}