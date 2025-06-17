package com.example.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.common_ui.BaseFragment
import com.example.common_ui.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.ui.screens.MovieDetailScreen
import com.example.movies.ui.databinding.FragmentMovieDetailBinding
import com.example.ui.view_model.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

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
        try {
            movieDetailViewModel.loadDetailMovies(
                arguments?.getString("movieId")?.toInt() ?: return
            )
            composeView.setContent {
                MovieDetailScreen(
                    state = movieDetailViewModel.detailMovies.collectAsState().value,
                    onError = {
                        replaceFragment(ErrorFragment())
                        movieDetailViewModel.setIdle()
                    })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}