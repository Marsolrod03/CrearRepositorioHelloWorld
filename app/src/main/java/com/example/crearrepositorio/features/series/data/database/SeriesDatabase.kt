package com.example.crearrepositorio.features.series.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crearrepositorio.features.series.data.database.dao.SeriesDao
import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity


@Database(entities = [SeriesEntity::class], version = 1)
abstract class SeriesDatabase : RoomDatabase() {
    abstract fun getSeriesDao(): SeriesDao

}