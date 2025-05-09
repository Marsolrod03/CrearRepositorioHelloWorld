package com.example.crearrepositorio.features.actors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.DetailsActorsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsActorFragment : BaseFragment<DetailsActorsBinding>() {
    private val binding get() = _binding!!
    private val viewModel: ActorDetailsViewModel by viewModels()
    private var actorId: String? = null
    private var actorName: String? = null
    private var actorImage: String? = null
    private var actorGender: String? = null
    private var actorPopularity: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailsActorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            actorId = it.getString("actorId")
            actorName = it.getString("actorName")
            actorImage = it.getString("actorImage")
            actorGender = it.getString("actorGender")
            actorPopularity = it.getString("actorPopularity")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorDetails.collect { actorDetails ->
                handleState(actorDetails)
            }
        }
        actorId?.let {
            viewModel.loadDetails(it)
        }
    }

    private fun handleState(actorDetails: DetailsState){
        when (actorDetails) {
            DetailsState.Idle -> Unit
            is DetailsState.Success -> {
                with(binding){
                    textViewName.text = actorName
                    textViewBiography.text = actorDetails.details.biography
                    textViewPopularity.text = "POPULARITY: $actorPopularity"
                    textViewGender.text = "GENDER: $actorGender"
                    imageViewActor.load(actorImage)
                }
            }
            is DetailsState.Error -> {
                replaceFragment(ErrorFragment())
                viewModel.resetStateToHome()
            }
        }
    }
}