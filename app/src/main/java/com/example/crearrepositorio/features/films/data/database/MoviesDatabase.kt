package com.example.crearrepositorio.features.films.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crearrepositorio.features.films.data.database.dao.MovieDAO
import com.example.crearrepositorio.features.films.data.database.entities.MovieEntity
import com.example.crearrepositorio.features.films.data.dto.MovieDTO

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO
}