package com.example.crearrepositorio.features.films.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.features.films.domain.use_case.GetMoviesUseCase
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private fun loadMovies() {
        viewModelScope.launch {
            getMoviesUseCase.getMovies()
                .catch { error ->
                    _movies.update {
                        MoviesState.Error(error.message ?: "Error al cargar las películas") }
                }
                .collect { movieList ->
                    _movies.update { MoviesState.Succeed(movieList) }
                }
        }
    }
}

sealed class MoviesState {
    data object Idle : MoviesState()
    data class Succeed(val movies: List<MovieModel>) : MoviesState()
    data class Error(val message: String) : MoviesState()
}
