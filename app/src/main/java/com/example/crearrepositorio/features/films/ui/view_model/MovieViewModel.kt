package com.example.crearrepositorio.features.films.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.films.domain.MovieWrapper
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.use_case.DeleteDatabaseUseCase
import com.example.crearrepositorio.features.films.domain.use_case.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val _movies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val movies: StateFlow<MoviesState> = _movies.asStateFlow()

    fun loadMovies() {

        if (_movies.value is MoviesState.Loader.LoadingMoreMovies ||
            _movies.value is MoviesState.Loader.LoadingFirstTime
        ) {
            return
        }

        viewModelScope.launch {
            getMoviesUseCase()
                .onStart {
                    _movies.update {
                        if ((_movies.value as? MoviesState.Succeed)?.movies?.isNotEmpty() == true) {
                            MoviesState.Loader.LoadingMoreMovies
                        } else {
                            MoviesState.Loader.LoadingFirstTime
                        }
                    }
                }
                .collect { result: Result<MovieWrapper> ->
                    result.onSuccess { movieWrapper ->
                        _movies.update {
                            MoviesState.Succeed(movieWrapper.movieList)
                        }
                    }
                    result.onFailure {
                        _movies.update { MoviesState.Error("Error loading more movies") }
                    }
                }
        }
    }

    fun setIdle() {
        _movies.value = MoviesState.Idle
    }

    fun onFragmentOnResume() {
        if (_movies.value is MoviesState.Idle) {
            loadMovies()
        }
    }
}

sealed class MoviesState {
    data object Idle : MoviesState()
    data class Succeed(val movies: List<MovieModel>) : MoviesState()
    data class Error(val message: String) : MoviesState()
    sealed class Loader {
        data object LoadingFirstTime : MoviesState()
        data object LoadingMoreMovies : MoviesState()
    }
}
