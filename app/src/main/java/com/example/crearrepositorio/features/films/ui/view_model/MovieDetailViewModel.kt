package com.example.crearrepositorio.features.films.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.use_case.GetDetailMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getDetailMoviesUseCase: GetDetailMoviesUseCase
): ViewModel(){
    private val _detailMovies = MutableStateFlow<DetailMoviesState>(DetailMoviesState.Idle)
    val detailMovies: StateFlow<DetailMoviesState> = _detailMovies.asStateFlow()

    fun loadDetailMovies(movieId: String){
        viewModelScope.launch {
            getDetailMoviesUseCase(movieId).collect{ response: Result<MovieModel> ->
                response.onSuccess {movieModel ->
                    _detailMovies.value = DetailMoviesState.Succeed(movieModel)
                }
                response.onFailure {
                    _detailMovies.value = DetailMoviesState.Error("Error loading actor details")
                }
            }
        }
    }

    fun setIdle(){
        _detailMovies.value = DetailMoviesState.Idle
    }
}

sealed class DetailMoviesState {
    data object Idle : DetailMoviesState()
    data class Succeed(val movies: MovieModel) : DetailMoviesState()
    data class Error(val message: String) : DetailMoviesState()
}