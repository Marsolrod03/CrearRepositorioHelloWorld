package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "series")
data class SeriesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster_path") val poster_path: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "vote_average") val vote_average: Double,
    @ColumnInfo(name = "vote_count") val vote_count: Int,
    @ColumnInfo(name = "first_air_date") val first_air_date: String
)
