package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "movie_pages_entities")
data class MoviePageEntity(
    @PrimaryKey
    @ColumnInfo(name = "page") val page: Int = 1,
    @ColumnInfo(name = "total_pages") val totalPages: Int = Int.MAX_VALUE,
    @ColumnInfo(name = "last_loaded_page") val lastLoadedPage: Int = 0,
    @ColumnInfo(name = "last_date_deleted") val lastDateDeleted: Long = Calendar.getInstance().timeInMillis
)