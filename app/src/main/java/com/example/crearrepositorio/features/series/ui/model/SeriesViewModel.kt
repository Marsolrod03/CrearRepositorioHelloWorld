package com.example.crearrepositorio.features.series.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.series.domain.use_case.GetSeriesUseCase
import com.example.crearrepositorio.features.series.domain.model.SerieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase,
) : ViewModel() {

    private val _seriesList = MutableStateFlow<SeriesState>(SeriesState.Idle)
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()

    init {
        loadSeries()
    }

    fun loadSeries() {
        if (_seriesList.value is SeriesState.PartialLoading ||
            _seriesList.value is SeriesState.FirstLoading
        ) {
            return
        }
        _seriesList.update {
            if ((_seriesList.value as? SeriesState.Created)?.series?.isNotEmpty() == true) {
                SeriesState.PartialLoading
            } else {
                SeriesState.FirstLoading
            }
        }
        viewModelScope.launch {
            getSeriesUseCase()
                .collect { result ->
                    result.onSuccess { seriesWrapper ->
                        _seriesList.update {
                            SeriesState.Created(seriesWrapper.listSeries)
                        }
                    }
                    result.onFailure {
                        _seriesList.update {
                            SeriesState.Error(it)
                        }
                    }
                }
        }
    }

    sealed class SeriesState {
        data object Idle : SeriesState()
        data class Created(val series: List<SerieModel>) : SeriesState()
        data class Error(val error: SeriesState) : SeriesState()
        data object PartialLoading : SeriesState()
        data object FirstLoading : SeriesState()
    }

}


