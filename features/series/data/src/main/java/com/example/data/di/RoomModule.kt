package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.data_source.DatabaseDataSource
import com.example.data.database.SeriesDatabase
import com.example.data.database.dao.PaginationSeriesDao
import com.example.data.database.dao.SeriesDao
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
        Room.databaseBuilder(context, SeriesDatabase::class.java, SERIES_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideSeriesDao(db: SeriesDatabase) = db.getSeriesDao()

    @Singleton
    @Provides
    fun providePaginationSeriesDao(db: SeriesDatabase) = db.getPaginationSeriesDao()


    @Singleton
    @Provides
    fun provideSeriesDataSource(
        seriesDao: SeriesDao,
        paginationSeriesDao: PaginationSeriesDao
    ) :
            DatabaseDataSource = DatabaseDataSource(seriesDao, paginationSeriesDao)




}