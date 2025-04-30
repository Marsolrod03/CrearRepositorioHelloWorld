package com.example.crearrepositorio.features.series.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.back
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    private val seriesViewModel: SeriesViewModel by viewModels()
    private val seriesAdapter = SeriesAdapter()
    private lateinit var linearLayoutManager: LinearLayoutManager

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

        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = seriesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItem =
                        linearLayoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount &&
                        firstVisibleItem >= 0 &&
                        seriesViewModel.seriesList.value !is SeriesViewModel.SeriesState.Loading) {
                        seriesViewModel.loadSeries()
                    }
                }
            })
        }
        viewLifecycleOwner.lifecycleScope.launch {
            seriesViewModel.seriesList.collect { seriesState ->
                handleState(seriesState)
            }
        }
    }

    private fun handleState(seriesState: SeriesViewModel.SeriesState) {
        when (seriesState) {
            SeriesViewModel.SeriesState.Idle -> Unit
            is SeriesViewModel.SeriesState.Created -> {
                seriesAdapter.updateSeries(seriesState.series)
            }
            is SeriesViewModel.SeriesState.Error -> {
                this.replaceFragment(ErrorFragment())
            }
            SeriesViewModel.SeriesState.Loading -> {}
        }
    }
}


