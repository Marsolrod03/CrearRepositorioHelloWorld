package com.example.crearrepositorio.features.actors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import com.example.crearrepositorio.features.actors.domain.ActorModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private lateinit var actorAdapter: ActorAdapter
    private val viewModel: ActorViewModel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayoutManager = GridLayoutManager(requireContext(), 2)

        actorAdapter = ActorAdapter { actor ->
            navigateToActorDetails(actor)
        }

        binding.recyclerViewList.apply {
            layoutManager = gridLayoutManager
            adapter = actorAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalItemCount = gridLayoutManager.itemCount
                    val firstVisibleItemPosition =
                        gridLayoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        viewModel.loadActors()
                    }
                }
            })
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorList.collect { actorsState ->
                handleState(actorsState)
            }
        }
    }

    private fun handleState(actorState: ActorState){
        when (actorState) {
            ActorState.Idle -> Unit
            is ActorState.Success -> {
                binding.loadingFullscreen.lottieLoadingFullscreen.visibility = View.GONE
                actorAdapter.updateActors(actorState.actors)
            }
            is ActorState.Error -> {
                actorAdapter.hideLoading()
                replaceFragment(ErrorFragment())
                viewModel.resetStateToHome()
            }
            is ActorState.PartialLoading -> {
                binding.loadingFullscreen.lottieLoadingFullscreen.visibility = View.GONE
                actorAdapter.showLoading()
            }
            is ActorState.FirstLoading -> {
                binding.loadingFullscreen.lottieLoadingFullscreen.visibility = View.VISIBLE
                actorAdapter.hideLoading()
            }
        }
    }

    private fun navigateToActorDetails(actor: ActorModel) {
        val bundle = Bundle().apply {
            putString("actorId", actor.id.toString())
            putString("actorName", actor.name)
            putString("actorImage", actor.image)
            putString("actorPopularity", actor.popularity.toString())
            putString("actorGender", actor.gender.toString())
        }
        val detailsFragment = DetailsActorFragment()
        detailsFragment.arguments = bundle
        replaceFragment(detailsFragment)
    }
}