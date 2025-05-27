package com.example.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MovieWrapper
import com.example.domain.model.MovieModel
import com.example.domain.use_case.GetMoviesUseCase
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

    private val _movies = MutableStateFlow<MoviesState>(MoviesState())
    val movies: StateFlow<MoviesState> = _movies.asStateFlow()
    private var hasMorePages = true

    init {
        loadMovies()
    }

    fun loadMovies() {
        if (hasMorePages
            && _movies.value.loader == null
        ) {
            viewModelScope.launch {
                getMoviesUseCase()
                    .onStart {
                        _movies.update {
                            if (_movies.value.succeedList.isNotEmpty()) {
                                movies.value.copy(
                                    loader = Loader.LoadingPartial,
                                    errorMessage = null
                                )
                            } else {
                                MoviesState(
                                    loader = Loader.LoadingFull,
                                )
                            }
                        }
                    }
                    .collect { result: Result<MovieWrapper> ->
                        result.onSuccess { movieWrapper ->
                            hasMorePages = movieWrapper.hasMorePages
                            _movies.update {
                                MoviesState(succeedList = movieWrapper.movieList)
                            }
                        }
                        result.onFailure {
                            _movies.update { MoviesState(errorMessage = "Error loading more movies") }
                        }
                    }
            }
        }
    }

}

data class MoviesState(
    val succeedList: List<MovieModel> = emptyList(),
    val errorMessage: String? = null,
    val loader: Loader? = null
)
sealed class Loader(){
    data object LoadingFull: Loader()
    data object LoadingPartial: Loader()
}
