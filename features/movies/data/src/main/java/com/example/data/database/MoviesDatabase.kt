package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.MovieDAO
import com.example.data.database.dao.MoviePageDAO
import com.example.data.database.entities.MovieEntity
import com.example.data.database.entities.MoviePageEntity

@Database(entities = [MovieEntity::class, MoviePageEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
    abstract fun getMoviePageDAO(): MoviePageDAO
}