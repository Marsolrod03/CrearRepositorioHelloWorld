package com.example.crearrepositorio.features.series.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.crearrepositorio.R
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.databinding.FragmentDetailsSeriesBinding
import com.example.crearrepositorio.features.series.domain.SerieModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsSeriesFragment : BaseFragment<FragmentDetailsSeriesBinding>() {
    private val viewModel: SeriesDetailsViewModel by viewModels()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsSeriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serieId = arguments?.getString("seriesId") ?: return
        viewModel.loadDetails(serieId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.serieDetails.collect { state ->
                when (state) {
                    is DetailsState.Created -> showSeriesDetails(state.serie)
                    is DetailsState.Error -> showError(state.message)
                    DetailsState.Idle -> Unit

                    }
                }
            }
        }

        private fun showSeriesDetails(serie: SerieModel) {
            with(binding) {
                title.text = serie.name
                overview.text = serie.overview
                poster.load(serie.poster_path)
                dateEmision.text = serie.first_air_date
                voteCount.text = serie.vote_count.toString()
                voteAverage.text = serie.vote_average.toString()
            }

        }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
    }


    }