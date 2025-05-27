package com.example.crearrepositorio.features.actors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common_ui.fragments.BaseFragment
import com.example.common_ui.fragments.ErrorFragment
import com.example.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private val actorAdapter = ActorAdapter()
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
                        firstVisibleItemPosition >= 0 &&
                        viewModel.actorList.value !is ActorState.Loading
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
                actorAdapter.updateActors(actorState.actors)
            }
            is ActorState.Error -> {
                replaceFragment(ErrorFragment())
            }
            is ActorState.Loading -> {

            }
        }
    }
}