package com.example.crearrepositorio.features.series.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.series.domain.GetSeriesUseCase
import com.example.crearrepositorio.features.series.domain.SerieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeriesViewModel : ViewModel() {
    private val _seriesList = MutableStateFlow<SeriesState>(SeriesState.Idle)
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()
    private val getSeriesUseCase = GetSeriesUseCase()

    init {
        loadSeries()
    }

    private fun loadSeries() {
        viewModelScope.launch {
            getSeriesUseCase()
                .collect { series ->
                _seriesList.update { SeriesState.Created(series) }
            }
        }
    }
}
    sealed class SeriesState {
     data object Idle : SeriesState()
     data class Created(val series: List<SerieModel>) : SeriesState()
   }

