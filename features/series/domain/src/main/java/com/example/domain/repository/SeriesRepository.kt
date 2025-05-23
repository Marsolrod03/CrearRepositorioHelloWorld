package com.example.domain.repository


import com.example.domain.SeriesWrapper
import com.example.domain.model.SerieModel
import kotlinx.coroutines.flow.Flow

interface SeriesRepository {
    fun getPagedSeriesFromApi(currentPage: Int): Flow<Result<SeriesWrapper>>
    suspend fun getAllSeriesFromDatabase(): List<SerieModel>

    fun getSeriesDetails(seriesId: String): Flow<Result<SerieModel>>
    suspend fun getSerieById(id: Int): SerieModel
    suspend fun clearSeries()


    suspend fun refreshData()
    suspend fun insertSeries(series: List<SerieModel>)

    suspend fun manageSeriesPagination() : Flow<Result<SeriesWrapper>>
    suspend fun manageSeriesDetails(seriesId: String) : Flow<Result<SerieModel>>

    suspend fun getPaginationSeries(): Int
    suspend fun insertPaginationSeries(lastLoadedPage: Int)
    suspend fun clearPaginationSeries()
    suspend fun updatePaginationSeries(newPage: Int)
    suspend fun getLastDatabaseDeletion(): Long
    suspend fun updateLastDatabaseDeletion(newLastDatabaseDeletion: Long)

    suspend fun getTotalPages(): Int
    suspend fun updateTotalPages(newTotalPages: Int)

}
