package com.example.movieDatabase.features.series.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieDatabase.features.series.domain.AppError
import com.example.movieDatabase.features.series.domain.GetSeriesUseCase
import com.example.movieDatabase.features.series.domain.SerieModel
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
                .catch { e -> val error =  if(e is AppError) e else AppError.UnknownError()
                    _seriesList.update { SeriesState.Error(error) } }
                .collect { series -> _seriesList.update { SeriesState.Created(series) } }
        }
    }
}

sealed class SeriesState {
    data object Idle : SeriesState()
    data class Created(val series: List<SerieModel>) : SeriesState()
    data class Error(val error: AppError) : SeriesState()
}

