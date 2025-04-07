package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.ui.adapter.MoviesAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.ui.viewModel.MovieViewModel
import com.example.crearrepositorio.ui.viewModel.MovieViewModel.MoviesState
import kotlinx.coroutines.launch

class FirstFragment : BaseFragment<FragmentFirstBinding>() {
    private val binding get() = _binding!!
    private val movieViewModel: MovieViewModel by activityViewModels()
    val moviesAdapter = MoviesAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.recycledMovies.adapter = moviesAdapter
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
        return binding.root
    }

    private fun manageMoviesState(moviesState: MoviesState) {
        when(moviesState){
            MoviesState.Idle -> {
            }
            is MoviesState.Succed->{
                moviesAdapter.updateMovies(moviesState.movies)
            }
        }
    }


}