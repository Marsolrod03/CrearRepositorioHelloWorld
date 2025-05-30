package com.example.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.SerieModel
import com.example.domain.use_case.GetSeriesDetailsUseCase
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

    private val _serieDetails = MutableStateFlow(SerieDetails())
    val serieDetails: StateFlow<SerieDetails> = _serieDetails.asStateFlow()


    fun loadDetails(seriesId: String) {
        viewModelScope.launch {
            _serieDetails.value = SerieDetails(isLoading = true)
            getSeriesDetailsUseCase(seriesId)
                .collect { result ->
                    result.onSuccess { serie ->
                        _serieDetails.value = SerieDetails(details = serie)
                    }
                    result.onFailure { exception ->
                        _serieDetails.value =
                            _serieDetails.value.copy(error = exception.message)
                    }

                }
        }
    }
}

data class SerieDetails(
    val isLoading: Boolean = false,
    val details: SerieModel? = null,
    val error: String? = null

)