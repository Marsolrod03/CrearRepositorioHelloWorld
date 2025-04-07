package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crearrepositorio.domain.MovieModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel(){

    private val _movies = MutableStateFlow<MoviesState>(MoviesState.Idle)
    val movies : StateFlow<MoviesState> = _movies.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies(){
        viewModelScope.launch {
            val movies = fillWithMovies()
            _movies.update { MoviesState.Succeed(movies) }
        }
    }

    fun fillWithMovies(): List<MovieModel>{
        return listOf(
            MovieModel("Titulo 1", "src1"),
            MovieModel("Titulo 2", "src2"),
            MovieModel("Titulo 3", "src3"),
            MovieModel("Titulo 4", "src4"),
            MovieModel("Titulo 5", "src5"),
            MovieModel("Titulo 6", "src6"),
        )
    }

    sealed class MoviesState{
        data object Idle : MoviesState()
        data class Succeed(val movies: List<MovieModel>) : MoviesState()
    }
}