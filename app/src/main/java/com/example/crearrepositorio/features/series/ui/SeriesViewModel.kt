package com.example.crearrepositorio.features.series.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.series.domain.GetSeriesUseCase
import com.example.crearrepositorio.features.series.domain.SerieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _seriesList = MutableStateFlow<SeriesState>(SeriesState.Idle)
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()

    init {
        loadSeries()
    }

    private fun loadSeries() {
        viewModelScope.launch {
            getSeriesUseCase()
                .catch { e -> _seriesList.update { SeriesState.Error } }
                .collect { series -> _seriesList.update { SeriesState.Created(series) } }
        }
    }
}

sealed class SeriesState {
    data object Idle : SeriesState()
    data class Created(val series: List<SerieModel>) : SeriesState()
    data object Error : SeriesState()
}

