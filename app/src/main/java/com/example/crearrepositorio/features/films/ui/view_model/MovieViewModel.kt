package com.example.crearrepositorio.features.films.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val movies: StateFlow<MoviesState> = _movies.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {

        if (_movies.value is MoviesState.Loading) {
            return
        }

        _movies.update { MoviesState.Loading }

        viewModelScope.launch {
            getMoviesUseCase().collect { result ->
                    result.onSuccess { actorWrapper ->
                        _movies.update {
                            MoviesState.Succeed(actorWrapper.movieList)
                        }
                    }
                    result.onFailure {
                        _movies.update { MoviesState.Error("Error loading more movies") }
                    }
            }
        }
    }
}

sealed class MoviesState {
    data object Idle : MoviesState()
    data class Succeed(val movies: List<MovieModel>) : MoviesState()
    data class Error(val message: String) : MoviesState()
    data object Loading : MoviesState()
}
