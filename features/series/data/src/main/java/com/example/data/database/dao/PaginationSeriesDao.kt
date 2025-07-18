package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.PaginationSeriesEntity


@Dao
interface PaginationSeriesDao {

    @Query("SELECT last_loaded_page FROM pagination_series")
    suspend fun getPaginationSeries(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationSeries(paginationSeriesEntity: PaginationSeriesEntity)

    @Query("DELETE FROM pagination_series")
    suspend fun clearPaginationSeries()

    @Query("UPDATE pagination_series SET last_loaded_page = :newPage WHERE id = 1")
    suspend fun updatePaginationSeries(newPage: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPagination(lastLoadedPage: Int){
        insertPaginationSeries(PaginationSeriesEntity(lastLoadedPage = lastLoadedPage))
    }

    @Query("SELECT last_database_deletion FROM pagination_series WHERE id = 1")
    suspend fun getLastDatabaseDeletion(): Long

    @Query("UPDATE pagination_series SET last_database_deletion = :newLastDatabaseDeletion WHERE id = 1")
    suspend fun updateLastDatabaseDeletion(newLastDatabaseDeletion: Long)

    @Query("SELECT total_pages FROM pagination_series WHERE id = 1")
    suspend fun getTotalPages(): Int

    @Query("UPDATE pagination_series SET total_pages = :newTotalPages WHERE id = 1")
    suspend fun updateTotalPages(newTotalPages: Int)

}

