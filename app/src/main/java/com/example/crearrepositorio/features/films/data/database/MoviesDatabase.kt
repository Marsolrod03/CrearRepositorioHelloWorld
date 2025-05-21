package com.example.crearrepositorio.features.films.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crearrepositorio.features.films.data.database.dao.MovieDAO
import com.example.crearrepositorio.features.films.data.database.dao.MoviePageDAO
import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import com.example.crearrepositorio.features.films.data.database.entities.MoviePageEntity
import com.example.crearrepositorio.features.films.data.dto.MovieDTO

@Database(entities = [MovieEntity::class, MoviePageEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
    abstract fun getMoviePageDAO(): MoviePageDAO
}