package com.example.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MovieModel
import com.example.domain.use_case.GetDetailMoviesUseCase
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
    private val _detailMovies = MutableStateFlow<DetailMoviesState>(DetailMoviesState())
    val detailMovies: StateFlow<DetailMoviesState> = _detailMovies.asStateFlow()

    fun loadDetailMovies(movieId: Int){
        viewModelScope.launch {
            getDetailMoviesUseCase(movieId).collect{ response: Result<MovieModel> ->
                response.onSuccess {movieModel ->
                    _detailMovies.value = DetailMoviesState(succeedMovie = movieModel)
                }
                response.onFailure {
                    _detailMovies.value = DetailMoviesState(errorMessage = "Error loading actor details")
                }
            }
        }
    }

    fun setIdle(){
        _detailMovies.value = DetailMoviesState(isIdle = true)
    }
}

data class DetailMoviesState(
    val isIdle: Boolean = true,
    val succeedMovie : MovieModel? = null,
    val errorMessage: String? = null
    )