package com.example.crearrepositorio.features.series.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.series.domain.use_case.GetSeriesDetailsUseCase
import com.example.crearrepositorio.features.series.domain.model.SerieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val getSeriesDetailsUseCase: GetSeriesDetailsUseCase
) : ViewModel() {

    private val _serieDetails = MutableStateFlow<DetailsState>(DetailsState.Idle)
    val serieDetails: StateFlow<DetailsState> = _serieDetails.asStateFlow()


    fun loadDetails(seriesId: String) {
        viewModelScope.launch {
            getSeriesDetailsUseCase(seriesId)
                .collect { result ->
                    result.onSuccess { serie ->
                        _serieDetails.value = DetailsState.Created(serie)
                    }
                    result.onFailure { exception ->
                        _serieDetails.value =
                            DetailsState.Error(exception.message ?: "Unknown error")
                    }

                }
        }
    }
}


sealed class DetailsState {
    data object Idle : DetailsState()
    data class Created(val details: SerieModel) : DetailsState()
    data class Error(val message: String) : DetailsState()

}