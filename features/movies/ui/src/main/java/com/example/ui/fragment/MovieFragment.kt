package com.example.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui.BaseFragment
import com.example.common_ui.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.ui.adapter.MoviesAdapter
import com.example.ui.databinding.FragmentFirstBinding
import com.example.ui.view_model.MovieViewModel
import com.example.ui.view_model.MoviesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentFirstBinding>() {
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager
    private val moviesAdapter = MoviesAdapter { movie ->
        val detailFragment = MovieDetailFragment()
        val bundle = Bundle().apply {
            putString("movieId", movie.id)
        }
        detailFragment.arguments = bundle
        replaceFragment(detailFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridLayoutManager = GridLayoutManager(requireContext(), 1)

        binding.recycledMovies.apply {
            layoutManager = gridLayoutManager
            adapter=moviesAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        gridLayoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        movieViewModel.loadMovies()
                    }
                }
            })
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieViewModel.movies.collect { moviesState ->
                    manageMoviesState(moviesState)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        movieViewModel.onFragmentOnResume()
    }

    private fun manageMoviesState(moviesState: MoviesState) {
        when (moviesState) {
            MoviesState.Idle -> Unit
            is MoviesState.Succeed -> {
                moviesAdapter.updateMovies(moviesState.movies)
                manageLoadingFullScreen(false)
                moviesAdapter.manageLoadingPartial(false)
            }
            is MoviesState.Error -> {
                moviesAdapter.manageLoadingPartial(false)
                manageLoadingFullScreen(false)
                this.replaceFragment(ErrorFragment())
                movieViewModel.setIdle()
            }
            is MoviesState.Loader.LoadingFirstTime -> {
                manageLoadingFullScreen(true)
                moviesAdapter.manageLoadingPartial(false)
            }
            is MoviesState.Loader.LoadingMoreMovies -> {
                 manageLoadingFullScreen(false)
                moviesAdapter.manageLoadingPartial(true)
            }
        }
    }

    private fun manageLoadingFullScreen(show: Boolean){
        if (show){
            binding.loadFirstTime.lottieLoadingFullscreen.visibility = View.VISIBLE
        }else{
            binding.loadFirstTime.lottieLoadingFullscreen.visibility = View.GONE
        }
    }
}
