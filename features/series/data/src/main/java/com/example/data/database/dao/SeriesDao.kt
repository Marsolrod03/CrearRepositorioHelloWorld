package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.SeriesEntity

@Dao
interface SeriesDao {

    @Query("SELECT * FROM series")
    suspend fun getAllSeries(): List<SeriesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(series: List<SeriesEntity>)

    @Query("SELECT * FROM series WHERE id = :id")
    suspend fun getSerieById(id: Int): SeriesEntity

    @Query("DELETE FROM series")
    suspend fun clearSeries()




}