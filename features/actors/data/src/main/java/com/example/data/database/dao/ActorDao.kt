package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.ActorEntity

@Dao
interface ActorDao {
    @Query("SELECT * FROM  actor_table ORDER BY popularity DESC")
    suspend fun getAllActors(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllActors(actors: List<ActorEntity>)

    @Query("DELETE FROM actor_table")
    suspend fun deleteAllActors()

    @Query("SELECT * FROM actor_table WHERE id = :actorId")
    suspend fun getActorById(actorId: Int): ActorEntity

    @Query("UPDATE actor_table SET biography = :biography WHERE id = :actorId")
    suspend fun updateActorBiography(actorId: Int, biography: String)
}