package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import com.example.crearrepositorio.ui.adapter.SeriesAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.ui.viewModel.SeriesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    private val seriesViewModel: SeriesViewModel by activityViewModels()
    private lateinit var seriesAdapter: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        seriesAdapter = SeriesAdapter(emptyList())
        binding.recyclerView.adapter = seriesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                seriesViewModel.series.collectLatest { series ->
                    seriesAdapter.updateSeries(series)
                }
            }
        }

        seriesViewModel.loadSeries()

        binding.btnHome.setOnClickListener { back() }

        return binding.root
    }
}