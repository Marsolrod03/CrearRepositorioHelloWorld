package com.example.crearrepositorio.features.films.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentMovieDetailBinding
import com.example.crearrepositorio.features.films.ui.MovieDetailScreen
import com.example.crearrepositorio.features.films.ui.view_model.DetailMoviesState
import com.example.crearrepositorio.features.films.ui.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieDetailViewModel.loadDetailMovies(arguments?.getString("movieId") ?: return)
        composeView.setContent {
            MovieDetailScreen(
                state = movieDetailViewModel.detailMovies.collectAsState().value,
                onError = {
                    replaceFragment(ErrorFragment())
                    movieDetailViewModel.setIdle()
                })
        }
    }
}