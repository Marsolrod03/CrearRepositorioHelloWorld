package com.example.crearrepositorio.features.series.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crearrepositorio.features.series.data.database.dao.PaginationSeriesDao
import com.example.crearrepositorio.features.series.data.database.dao.SeriesDao
import com.example.crearrepositorio.features.series.data.database.entities.PaginationSeriesEntity
import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity


@Database(entities = [SeriesEntity::class, PaginationSeriesEntity::class], version = 2, exportSchema = false)
abstract class SeriesDatabase : RoomDatabase() {
    abstract fun getSeriesDao(): SeriesDao
    abstract fun getPaginationSeriesDao(): PaginationSeriesDao

}