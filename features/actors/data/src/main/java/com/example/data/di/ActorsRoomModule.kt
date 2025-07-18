package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.data_source.ActorsLocalDataSource
import com.example.data.database.ActorDatabase
import com.example.data.database.dao.ActorDao
import com.example.data.database.dao.PaginationActorsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActorsRoomModule {

    private const val ACTOR_DATABASE_NAME = "actor_table"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ActorDatabase::class.java, ACTOR_DATABASE_NAME)
            .fallbackToDestructiveMigration(false)
            .build()

    @Singleton
    @Provides
    fun provideActorDao(db: ActorDatabase) = db.getActorsDao()

    @Singleton
    @Provides
    fun providePaginationActorsDao(db: ActorDatabase) = db.getPaginationActorsDao()

    @Singleton
    @Provides
    fun provideDatabaseDataSource(
        actorDao: ActorDao,
        paginationActorsDao: PaginationActorsDao
    ): ActorsLocalDataSource =
        ActorsLocalDataSource(actorDao, paginationActorsDao)
}