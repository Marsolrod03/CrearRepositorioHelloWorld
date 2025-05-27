package com.example.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import com.example.common_ui.BaseFragment
import com.example.common_ui.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.domain.models.ActorModel
import com.example.ui.databinding.FragmentThirdBinding
import com.example.ui.details.DetailsActorFragment
import com.example.ui.screens.ActorsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentThirdBinding>() {
    private val viewModel: ActorViewModel by viewModels()

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            val state by viewModel.actorList.collectAsState()
            ActorsScreen(
                actorState = state,
                onNavigateToActorDetails = { actor -> navigateToActorDetails(actor) },
                onError = {
                    replaceFragment(ErrorFragment())
                    viewModel.resetStateToHome()
                },
                onLoadActors = {
                    viewModel.loadActors()
                }
            )
        }
    }

    private fun navigateToActorDetails(actor: ActorModel) {
        val bundle = Bundle().apply {
            putString("actorId", actor.id.toString())
            putString("actorImage", actor.image)
            putString("actorGender", actor.gender.toString())
        }
        val detailsFragment = DetailsActorFragment()
        detailsFragment.arguments = bundle
        replaceFragment(detailsFragment)
    }
}