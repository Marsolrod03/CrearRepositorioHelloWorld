package com.example.movieDatabase.features.series.data

import com.example.movieDatabase.features.series.domain.SerieModel
import com.example.movieDatabase.features.series.domain.SeriesRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class SeriesRepositoryImpl
@Inject
constructor(
    private val networkDataSource: SeriesNetworkDataSource
) : SeriesRepository {

    override suspend fun getAllSeries(): Flow<List<SerieModel>> = flow {
        val result = networkDataSource.fetchSeries()
        emit(result.map { it.toSeriesModel() })
    }
}
