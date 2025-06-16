package com.example.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.common_ui.BaseFragment
import com.example.common_ui.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.series.ui.databinding.FragmentDetailsSeriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSeriesFragment : BaseFragment<FragmentDetailsSeriesBinding>() {
    protected val viewModel: SeriesDetailsViewModel by viewModels()
    private var seriesId: String? = null
    private var seriesName: String? = null
    private var seriesOverview: String? = null
    private var seriesPosterPath: String? = null
    private var seriesFirstAirDate: String? = null
    private var seriesVoteCount: Int? = null
    private var seriesVoteAverage: Double? = null

    private lateinit var composeView: ComposeView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            seriesId = it.getString("seriesId")
            seriesName = it.getString("seriesName")
            seriesOverview = it.getString("seriesOverview")
            seriesPosterPath = it.getString("seriesPoster")
            seriesFirstAirDate = it.getString("seriesFirstAirDate")
            seriesVoteCount = it.getInt("seriesVoteCount")
            seriesVoteAverage = it.getDouble("seriesVoteAverage")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesId?.let {
            viewModel.loadDetails(it)
        }

        composeView.setContent {
            val state = viewModel.serieDetails.collectAsState()

            SeriesDetailInfo(
                poster = seriesPosterPath ?: "",
                dateEmision = seriesFirstAirDate ?: "",
                overview = seriesOverview ?: "",
                voteCount = seriesVoteCount.toString(),
                voteAverage = seriesVoteAverage.toString(),
                detailsState = state.value,
                onError = {
                    replaceFragment(ErrorFragment())
                }

            )
        }


    }
}