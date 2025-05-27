package com.example.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.common_ui.fragments.BaseFragment
import com.example.common_ui.fragments.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.ui.adapter.MoviesAdapter
import com.example.ui.databinding.FragmentFirstBinding
import com.example.ui.screens.MovieListScreen
import com.example.ui.view_model.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentFirstBinding>() {
    private lateinit var composeView: ComposeView
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            MovieListScreen(
                state = movieViewModel.movies.collectAsState().value,
                onClick = { movie ->
                    val detailFragment = MovieDetailFragment()
                    val bundle = Bundle().apply {
                        putString("movieId", movie.id)
                    }
                    detailFragment.arguments = bundle
                    replaceFragment(detailFragment)
                },
                onLoad = {movieViewModel.loadMovies()},
                onError = {
                    replaceFragment(ErrorFragment())
                }
            )
        }
    }

//    override fun onResume() {
//        super.onResume()
//        movieViewModel.onFragmentOnResume()
//    }


}
