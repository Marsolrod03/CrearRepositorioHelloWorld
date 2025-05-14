package com.example.crearrepositorio.features.series.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crearrepositorio.features.series.data.database.entities.SeriesEntity

@Dao
interface SeriesDao {

    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<SeriesEntity>)

}