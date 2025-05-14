package com.example.crearrepositorio.features.actors.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crearrepositorio.features.actors.data.database.dao.ActorDao
import com.example.crearrepositorio.features.actors.data.database.dao.PaginationActorsDao
import com.example.crearrepositorio.features.actors.data.database.entities.ActorEntity
import com.example.crearrepositorio.features.actors.data.database.entities.PaginationActorsEntity

@Database(entities = [ActorEntity::class, PaginationActorsEntity::class], version = 3)
abstract class ActorDatabase: RoomDatabase() {
    abstract fun getActorsDao(): ActorDao
    abstract fun getPaginationActorsDao(): PaginationActorsDao
}