package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import com.example.crearrepositorio.ui.adapter.SeriesAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.ui.viewModel.SeriesState
import com.example.crearrepositorio.ui.viewModel.SeriesViewModel
import kotlinx.coroutines.launch

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    private val seriesViewModel: SeriesViewModel by viewModels()
    private val seriesAdapter = SeriesAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.recyclerView.adapter = seriesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                seriesViewModel.seriesList.collect { seriesState ->
                    stateHandler(seriesState)
                }
            }
        }

        binding.btnHome.setOnClickListener { back() }

        return binding.root
    }

    fun stateHandler(seriesState: SeriesState) {
        when (seriesState) {
            SeriesState.Idle -> {}
            is SeriesState.Created -> {
                seriesAdapter.updateSeries(seriesState.series)
            }

        }
    }
}