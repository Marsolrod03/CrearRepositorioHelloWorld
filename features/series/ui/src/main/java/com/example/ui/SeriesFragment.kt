package com.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.common_ui.BaseFragment
import com.example.common_ui.back
import com.example.common_ui.replaceFragment
import com.example.domain.model.SerieModel
import com.example.ui.databinding.FragmentSecondBinding
import com.example.ui.details.DetailsSeriesFragment
import com.example.ui.model.SeriesViewModel
import com.example.ui.screens.SeriesScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeriesFragment : BaseFragment<FragmentSecondBinding>() {
    private val seriesViewModel: SeriesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SeriesScreen(
                    seriesViewModel,
                    onSeriesClicked = { series -> navigateToSeriesDetails(series) },
                    onNavigateHome = { back() }
                )
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


