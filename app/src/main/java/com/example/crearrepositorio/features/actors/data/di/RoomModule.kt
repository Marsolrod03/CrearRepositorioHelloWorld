package com.example.crearrepositorio.features.actors.data.di

import android.content.Context
import androidx.room.Room
import com.example.crearrepositorio.features.actors.data.data_source.DatabaseDataSource
import com.example.crearrepositorio.features.actors.data.database.ActorDatabase
import com.example.crearrepositorio.features.actors.data.database.dao.ActorDao
import com.example.crearrepositorio.features.actors.data.database.dao.PaginationActorsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

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
    ): DatabaseDataSource =
        DatabaseDataSource(actorDao, paginationActorsDao)
}