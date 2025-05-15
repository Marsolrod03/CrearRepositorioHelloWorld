package com.example.crearrepositorio.features.films.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import com.example.crearrepositorio.features.films.data.dto.MovieDTO

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies_entities")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies_entities")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies_entities WHERE id = :movieId")
    suspend fun getMovieDetails(movieId: Int): MovieEntity
}