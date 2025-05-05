package com.example.crearrepositorio.features.films.ui.fragment

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
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.back
import com.example.crearrepositorio.common_ui.replaceFragmentWithoutBackStack
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.features.films.ui.view_model.MovieViewModel
import com.example.crearrepositorio.features.films.ui.adapter.MoviesAdapter
import com.example.crearrepositorio.features.films.ui.view_model.MoviesState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FirstFragment : BaseFragment<FragmentFirstBinding>() {
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by viewModels()
    private val moviesAdapter = MoviesAdapter()
    private lateinit var gridLayoutManager: GridLayoutManager

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
                        firstVisibleItemPosition >= 0 &&
                        movieViewModel.movies.value !is MoviesState.Loading
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

        binding.btnHome.setOnClickListener {
            back()
        }
    }

    private fun manageMoviesState(moviesState: MoviesState) {
        when (moviesState) {
            MoviesState.Idle -> Unit
            is MoviesState.Succeed -> {
                moviesAdapter.updateMovies(moviesState.movies)
            }
            is MoviesState.Error -> {
                this.replaceFragmentWithoutBackStack(ErrorFragment())
            }
            MoviesState.Loading -> Unit
        }
    }
}