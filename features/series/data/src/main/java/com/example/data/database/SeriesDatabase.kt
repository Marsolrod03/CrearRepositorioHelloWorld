package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.PaginationSeriesDao
import com.example.data.database.dao.SeriesDao
import com.example.data.database.entities.PaginationSeriesEntity
import com.example.data.database.entities.SeriesEntity


@Database(entities = [SeriesEntity::class, PaginationSeriesEntity::class], version = 3, exportSchema = false)
abstract class SeriesDatabase : RoomDatabase() {
    abstract fun getSeriesDao(): SeriesDao
    abstract fun getPaginationSeriesDao(): PaginationSeriesDao

}