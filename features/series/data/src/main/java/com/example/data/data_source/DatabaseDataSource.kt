package com.example.data.data_source


import com.example.data.database.dao.PaginationSeriesDao
import com.example.data.database.dao.SeriesDao
import com.example.data.database.entities.SeriesEntity
import com.example.data.toSeriesModel
import javax.inject.Inject

class DatabaseDataSource @Inject constructor(
    private val seriesDao: SeriesDao,
    private val paginationSeriesDao: PaginationSeriesDao
) {

    suspend fun getSeries() = seriesDao.getAllSeries()
    suspend fun insertSeries(series: List<SeriesEntity>) = seriesDao.insertAll(series)
    suspend fun getSerieById(id: Int) = seriesDao.getSerieById(id).toSeriesModel()
    suspend fun clearSeries() = seriesDao.clearSeries()


    suspend fun getPaginationSeries() = paginationSeriesDao.getPaginationSeries()
    suspend fun insertPagination(lastLoadedPage: Int) = paginationSeriesDao.insertPagination(lastLoadedPage)
    suspend fun clearPaginationSeries() = paginationSeriesDao.clearPaginationSeries()
    suspend fun updatePaginationSeries(newPage: Int) = paginationSeriesDao.updatePaginationSeries(newPage)
    suspend fun getLastDatabaseDeletion() = paginationSeriesDao.getLastDatabaseDeletion()
    suspend fun updateLastDatabaseDeletion(newLastDatabaseDeletion: Long) = paginationSeriesDao.updateLastDatabaseDeletion(newLastDatabaseDeletion)

    suspend fun getTotalPages() = paginationSeriesDao.getTotalPages()
    suspend fun updateTotalPages(newTotalPages: Int) = paginationSeriesDao.updateTotalPages(newTotalPages)




}