package com.example.crearrepositorio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.example.crearrepositorio.databinding.FragmentHomeBinding
import com.example.crearrepositorio.ui.viewModel.MainViewModel

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
    }

    private fun setClickListeners(){
        binding.button.setOnClickListener { viewModel.navigateToFilms() }
        binding.button2.setOnClickListener { viewModel.navigateToSeries() }
        binding.button3.setOnClickListener { viewModel.navigateToActors() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}