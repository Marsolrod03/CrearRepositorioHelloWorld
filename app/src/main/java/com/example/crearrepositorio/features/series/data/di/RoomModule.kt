package com.example.crearrepositorio.features.series.data.di

import android.content.Context
import androidx.room.Room
import com.example.crearrepositorio.features.series.data.database.SeriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    private val SERIES_DATABASE_NAME = "series_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, SeriesDatabase::class.java, SERIES_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSeriesDao(db: SeriesDatabase) = db.getSeriesDao()

}