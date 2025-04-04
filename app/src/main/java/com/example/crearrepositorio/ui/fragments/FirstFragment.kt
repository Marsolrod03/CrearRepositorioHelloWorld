package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.ui.adapter.MoviesAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.domain.MovieModel
import com.example.crearrepositorio.ui.viewModel.MovieViewModel

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
        movieViewModel.movies.observe(viewLifecycleOwner, Observer{
            movies-> moviesAdapter.updateMovies(movies)
        })
        movieViewModel.loadFilms()

        binding.btnHome.setOnClickListener {
            back()
        }
        val view = binding.root
        return view
    }

}