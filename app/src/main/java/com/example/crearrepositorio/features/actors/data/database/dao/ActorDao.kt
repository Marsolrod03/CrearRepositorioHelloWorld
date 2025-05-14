package com.example.crearrepositorio.features.actors.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crearrepositorio.features.actors.data.database.entities.ActorEntity

@Dao
interface ActorDao {
    @Query("SELECT * FROM  actor_table ORDER BY popularity DESC")
    suspend fun getAllActors(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActors(actors: List<ActorEntity>)

    @Query("DELETE FROM actor_table")
    suspend fun deleteAllActors()
}