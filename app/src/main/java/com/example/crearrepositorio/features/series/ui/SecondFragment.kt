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
import com.example.crearrepositorio.features.series.domain.SerieModel
import com.example.crearrepositorio.features.series.ui.SeriesViewModel.SeriesState
import com.example.crearrepositorio.features.series.ui.details.DetailsSeriesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    private val seriesViewModel: SeriesViewModel by viewModels()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var seriesAdapter: SeriesAdapter


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
        seriesAdapter = SeriesAdapter { series -> navigateToSeriesDetails(series) }

        binding.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = seriesAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = linearLayoutManager.childCount
                    val totalItemCount = linearLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        linearLayoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
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

    private fun handleState(seriesState: SeriesState) {
        when (seriesState) {
            SeriesState.Idle -> Unit
            is SeriesState.Created -> {
                binding.loadingContainer.visibility = View.GONE
                seriesAdapter.updateSeries(seriesState.series)
            }

            is SeriesState.Error -> {
                seriesAdapter.showLoading()
                binding.loadingContainer.visibility = View.GONE
                this.replaceFragment(ErrorFragment())
            }

            is SeriesState.PartialLoading -> {
                binding.loadingContainer.visibility = View.VISIBLE
                seriesAdapter.showLoading()

            }

            is SeriesState.FirstLoading -> {
                binding.loadingContainer.visibility = View.VISIBLE
                seriesAdapter.showLoading()
            }
        }
    }

    private fun navigateToSeriesDetails(series: SerieModel) {
        val bundle = Bundle().apply {
            putString("seriesId", series.id.toString())
            putString("seriesName", series.name)
            putString("seriesPoster", series.poster_path)
            putString("seriesOverview", series.overview)
            putDouble("seriesVoteAverage", series.vote_average)
            putInt("seriesVoteCount", series.vote_count)
            putString("seriesFirstAirDate", series.first_air_date)

        }
        val detailsFragment = DetailsSeriesFragment()
        detailsFragment.arguments = bundle
        this.replaceFragment(detailsFragment)
    }
}


