package com.example.crearrepositorio.features.films.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentMovieDetailBinding
import com.example.crearrepositorio.features.films.ui.view_model.DetailMoviesState
import com.example.crearrepositorio.features.films.ui.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val binding get() = _binding!!
    private val movieDetailViewModel : MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieId = arguments?.getString("movieId")?:return

        movieDetailViewModel.loadDetailMovies(movieId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieDetailViewModel.detailMovies.collect { moviesState ->
                    manageDetailMovieState(moviesState)
                }
            }
        }
    }

    fun manageDetailMovieState(state: DetailMoviesState){
        with(binding) {
            when(state){
                DetailMoviesState.Idle -> Unit
                is DetailMoviesState.Error -> {
                    replaceFragment(ErrorFragment())
                    movieDetailViewModel.setIdle()
                }
                is DetailMoviesState.Succeed -> {
                    with (state) {
                        movieTitle.text = movies.title
                        movieImage.load("https://image.tmdb.org/t/p/w500${movies.backdrop_path}")
                        movieOverview.text = movies.overview
                        moviePopularity.text = "Popularity: ${movies.popularity}"
                        movieReleaseDate.text = "Release Date: ${movies.release_date}"
                        movieVoteAverage.text = "Vote Average: ${movies.vote_average}"
                    }
                }
            }
        }
    }
}