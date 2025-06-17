package com.example.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.Gender

@Entity(tableName = "actor_table")
data class ActorEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "gender") val gender: Gender,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "biography") val biography: String = "Info"

)