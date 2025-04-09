package com.example.crearrepositorio.domain

import com.example.crearrepositorio.R
import com.example.crearrepositorio.data.SeriesRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSeriesUseCase () {
    private val repository = SeriesRepositoryImpl()
    operator fun invoke(): Flow<List<SerieModel>>  { return repository.getAllSeries() }

}
