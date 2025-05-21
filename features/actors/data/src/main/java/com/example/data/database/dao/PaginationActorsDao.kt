package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.PaginationActorsEntity

@Dao
interface PaginationActorsDao {
    @Query("SELECT last_loaded_page FROM pagination_actors WHERE id = 1")
    suspend fun getPaginationActors(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationActors(paginationActors: PaginationActorsEntity)

    @Query("DELETE FROM pagination_actors")
    suspend fun clearPaginationActors()

    @Query("UPDATE pagination_actors SET last_loaded_page = :newPage WHERE id = 1")
    suspend fun updateLastLoadedPage(newPage: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagination() {
        insertPaginationActors(PaginationActorsEntity())
    }

    @Query("SELECT last_database_deletion FROM pagination_actors WHERE id = 1")
    suspend fun getLastDeletion(): Long

    @Query("UPDATE pagination_actors SET last_database_deletion = :lastDeletion WHERE id = 1")
    suspend fun updateLastDeletion(lastDeletion: Long)
}