package com.example.ui.details

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
import com.example.actors.ui.databinding.DetailsActorsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActorFragment : BaseFragment<DetailsActorsBinding>() {
    private val viewModel: ActorDetailsViewModel by viewModels()
    private var actorId: String? = null
    private var actorImage: String? = null
    private var actorGender: String? = null

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
            actorId = it.getString("actorId")
            actorImage = it.getString("actorImage")
            actorGender = it.getString("actorGender")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actorId?.let {
            viewModel.loadDetails(it)
        }

        composeView.setContent {
            val state by viewModel.actorDetails.collectAsState()
            ActorDetailsScreen(
                actorImage = actorImage,
                actorGender = actorGender,
                detailsState = state,
                onError = {
                    replaceFragment(ErrorFragment())
                    viewModel.resetStateToHome()
                }
            )
        }
    }

    companion object {
        fun newInstance(actorId: String, actorImage: String? = null, actorGender: String? = null): DetailsActorFragment {
            val fragment = DetailsActorFragment()
            val args = Bundle().apply {
                putString("actorId", actorId)
                putString("actorImage", actorImage)
                putString("actorGender", actorGender)
            }
            fragment.arguments = args
            return fragment
        }
    }
}