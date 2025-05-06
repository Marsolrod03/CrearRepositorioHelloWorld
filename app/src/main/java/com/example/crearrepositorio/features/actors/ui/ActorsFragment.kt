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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorsFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private val actorAdapter = ActorAdapter()
    private val viewModel: ActorViewModel by viewModels()
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.loadingPartial.lottieLoadingPartial.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridLayoutManager = GridLayoutManager(requireContext(), 2)

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
                delay(1000)
                handleState(actorsState)
            }
        }
    }

    private fun handleState(actorState: ActorState){
        when (actorState) {
            ActorState.Idle -> Unit
            is ActorState.Success -> {
                actorAdapter.updateActors(actorState.actors)
                binding.loadingPartial.lottieLoadingPartial.visibility = View.GONE
            }
            is ActorState.Error -> {
                binding.loadingPartial.lottieLoadingPartial.visibility = View.GONE
                replaceFragment(ErrorFragment())
            }
            is ActorState.Loading -> {
                binding.loadingPartial.lottieLoadingPartial.visibility = View.VISIBLE
                binding.loadingFullscreen.lottieLoadingFullscreen.visibility = View.GONE
            }
            is ActorState.FirstLoading -> {
                binding.loadingFullscreen.lottieLoadingFullscreen.visibility = View.VISIBLE
                binding.loadingPartial.lottieLoadingPartial.visibility = View.GONE
            }
        }
    }
}