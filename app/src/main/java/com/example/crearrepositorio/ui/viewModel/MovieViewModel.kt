package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.domain.entities.MovieModel
import com.example.crearrepositorio.domain.usecases.GetMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel(){

    private val getMoviesUseCase = GetMoviesUseCase()
    private val _movies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val movies : StateFlow<MoviesState> = _movies.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies(){
        viewModelScope.launch {
            getMoviesUseCase.getMovies().collect {movieList ->
                _movies.update {
                    MoviesState.Succeed(movieList)
                }
            }
        }
    }

    sealed class MoviesState{
        data object Idle : MoviesState()
        data class Succeed(val movies: List<MovieModel>) : MoviesState()
    }
}