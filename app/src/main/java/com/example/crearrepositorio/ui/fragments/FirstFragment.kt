package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.ui.adapter.MoviesAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.data.model.Movie

class FirstFragment : BaseFragment<FragmentFirstBinding>() {
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.recycledMovies.adapter = MoviesAdapter(
             listOf(
                 Movie("Titulo 1", "src1"),
                 Movie("Titulo 2", "src2"),
                 Movie("Titulo 3", "src3"),
                 Movie("Titulo 4", "src4"),
                 Movie("Titulo 5", "src5"),
                 Movie("Titulo 6", "src6"),
             )
        )
        binding.btnHome.setOnClickListener {
            back()
        }
        val view = binding.root
        return view
    }
}