package com.example.crearrepositorio.features.actors.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.crearrepositorio.features.actors.data.database.entities.PaginationActorsEntity

@Dao
interface PaginationActorsDao {
    @Query("SELECT last_loaded_page FROM pagination_actors")
    suspend fun getPaginationActors(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationActors(paginationActors: PaginationActorsEntity)

    @Query("DELETE FROM pagination_actors")
    suspend fun clearPaginationActors()

    @Query("UPDATE pagination_actors SET last_loaded_page = :newPage WHERE id = 1")
    suspend fun updateLastLoadedPage(newPage: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagination(lastPageLoaded: Int) {
        insertPaginationActors(PaginationActorsEntity(lastLoadedPage = lastPageLoaded))
    }
}