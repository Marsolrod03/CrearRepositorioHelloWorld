package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

@Entity(tableName = "pagination_actors")
data class PaginationActorsEntity(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "last_loaded_page") val lastLoadedPage: Int = 0,
    @ColumnInfo(name = "last_database_deletion") val lastDatabaseClearedAt: Long = Calendar.getInstance().timeInMillis
)