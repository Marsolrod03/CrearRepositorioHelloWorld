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
import java.io.IOException
import java.net.UnknownHostException

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val getSeriesUseCase: GetSeriesUseCase,
) : ViewModel() {

    private val _seriesList = MutableStateFlow(SeriesState())
    val seriesList: StateFlow<SeriesState> = _seriesList.asStateFlow()
    private var hashMorePages = true


    init {
        loadSeries()
    }


    fun loadSeries() {
        val currentState = _seriesList.value
        if (!hashMorePages || currentState.isPartialLoading || currentState.isFirsLoading) return

        viewModelScope.launch {
            getSeriesUseCase()
                .onStart {
                    _seriesList.update {
                        it.copy(
                            isFirsLoading = it.series.isEmpty(),
                            isPartialLoading = it.series.isNotEmpty(),
                            error = null,
                            errorMessage = null
                        )
                    }
                }
                .collect { result ->
                    result.onSuccess { seriesWrapper ->
                        hashMorePages = seriesWrapper.hashMorePages
                        _seriesList.update { current ->
                            current.copy(
                                series = seriesWrapper.listSeries,
                                isFirsLoading = false,
                                isPartialLoading = false,
                                error = null,
                                errorMessage = null
                            )
                        }
                    }
                    result.onFailure { throwable ->
                        val error = mapThrowableError(throwable)
                        _seriesList.update {
                            it.copy(
                                error = error,
                                errorMessage = error.message,
                                isFirsLoading = false,
                                isPartialLoading = false

                            )
                        }
                    }
                }
        }
    }

    fun clearError() {
        _seriesList.update {
            it.copy(error = null, errorMessage = null)
        }
    }
}

data class SeriesState(
    val series : List<SerieModel> = emptyList(),
    val isFirsLoading : Boolean = false,
    val isPartialLoading : Boolean = false,
    val error : AppError? = null,
    val errorMessage : String? = null

)

private fun mapThrowableError(throwable: Throwable): AppError {
    return when (throwable) {
        is AppError -> throwable
        is IOException -> AppError.NoInternet
        is UnknownHostException -> AppError.NoInternet
        else -> AppError.UnknownError(throwable.message)
    }
}



