package com.example.crearrepositorio.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crearrepositorio.domain.MovieModel

class MovieViewModel : ViewModel(){

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies get() = _movies

    fun loadFilms(){
        _movies.value = fakeMovies()
    }

    private fun fakeMovies(): List<MovieModel>{
        return listOf(
            MovieModel("Titulo 1", "src1"),
            MovieModel("Titulo 2", "src2"),
            MovieModel("Titulo 3", "src3"),
            MovieModel("Titulo 4", "src4"),
            MovieModel("Titulo 5", "src5"),
            MovieModel("Titulo 6", "src6"),
        )
    }
}