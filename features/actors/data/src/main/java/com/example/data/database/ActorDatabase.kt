package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.ActorDao
import com.example.data.database.dao.PaginationActorsDao
import com.example.data.database.entities.ActorEntity
import com.example.data.database.entities.PaginationActorsEntity

@Database(entities = [ActorEntity::class, PaginationActorsEntity::class], version = 5, exportSchema = false)
abstract class ActorDatabase: RoomDatabase() {
    abstract fun getActorsDao(): ActorDao
    abstract fun getPaginationActorsDao(): PaginationActorsDao
}