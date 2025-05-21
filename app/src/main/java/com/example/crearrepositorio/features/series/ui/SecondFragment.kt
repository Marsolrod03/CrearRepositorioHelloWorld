package com.example.crearrepositorio.features.series.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib.common_ui.BaseFragment
import com.example.lib.common_ui.ErrorFragment
import com.example.lib.common_ui.back
import com.example.lib.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    private val seriesViewModel: SeriesViewModel by viewModels()
    private val seriesAdapter = SeriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener { back() }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = seriesAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            seriesViewModel.seriesList.collect { seriesState ->
                handleState(seriesState)
            }
        }
    }

    private fun handleState(seriesState: SeriesState) {
        when (seriesState) {
            SeriesState.Idle -> Unit
            is SeriesState.Created -> {
                seriesAdapter.updateSeries(seriesState.series)
            }
            is SeriesState.Error -> {
                this.replaceFragment(ErrorFragment())
            }
        }
    }
}


