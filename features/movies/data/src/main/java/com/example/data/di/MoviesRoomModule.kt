package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MoviesRoomModule {

    private const val MOVIE_DATABASE_NAME = "movie_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MOVIE_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun providesMovieDAO(db: MoviesDatabase) = db.getMovieDAO()

    @Singleton
    @Provides
    fun providesMoviePageDAO (db : MoviesDatabase) = db.getMoviePageDAO()
}