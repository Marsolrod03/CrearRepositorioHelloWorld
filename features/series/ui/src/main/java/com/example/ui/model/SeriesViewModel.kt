package com.example.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.AppError
import com.example.domain.model.SerieModel
import com.example.domain.use_case.GetSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase,
) : ViewModel() {

    private val _seriesList = MutableStateFlow<SeriesState>(SeriesState.Idle)
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()
    private var hashMorePages = true


    init {
        loadSeries()
    }


    fun loadSeries() {
        if (!hashMorePages ||
            _seriesList.value is SeriesState.PartialLoading ||
            _seriesList.value is SeriesState.FirstLoading
        ) {
            return
        }
        viewModelScope.launch {
            getSeriesUseCase()
                .onStart {
                    _seriesList.update {
                        if ((it as? SeriesState.Created)?.series?.isNotEmpty() == true) {
                            SeriesState.PartialLoading(it.series)
                        } else {
                            SeriesState.FirstLoading
                        }
                    }
                }
                .collect { result ->
                    result.onSuccess { seriesWrapper ->
                        hashMorePages = seriesWrapper.hashMorePages
                        _seriesList.update {
                            SeriesState.Created(seriesWrapper.listSeries)
                        }
                    }
                    result.onFailure { error ->
                        _seriesList.value = SeriesState.Error(
                            appError = AppError.UnknownError(),
                            message = error.message
                        )
                    }
                }
        }
    }
}

    sealed class SeriesState {
        data object Idle : SeriesState()
        data class Created(val series: List<SerieModel>) : SeriesState()
        data class Error(val appError: AppError, val message: String? = null) : SeriesState()
        data class PartialLoading(val series: List<SerieModel>) : SeriesState()
        data object FirstLoading : SeriesState()
    }

