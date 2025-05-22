package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.MoviePageEntity

@Dao
interface MoviePageDAO {
    @Query("SELECT last_loaded_page FROM movie_pages_entities")
    suspend fun getLastMoviePage(): Int

    @Query("DELETE FROM movie_pages_entities")
    suspend fun deleteAllMoviePages()

    @Query("UPDATE movie_pages_entities SET last_loaded_page = :newPage WHERE page = 1")
    suspend fun updateLastLoadedPage(newPage: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviePage(moviePageEntity: MoviePageEntity)

    @Query("SELECT last_date_deleted FROM movie_pages_entities WHERE page = 1")
    suspend fun getLastDelete(): Long

    @Query("UPDATE movie_pages_entities SET last_date_deleted = :lastDelete WHERE page = 1")
    suspend fun updateLastDelete(lastDelete: Long)
}
