package ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import kotlinx.coroutines.launch
import ui.adapter.ActorAdapter
import ui.viewModel.ActorState
import ui.viewModel.ActorViewModel

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private val actorAdapter = ActorAdapter()
    private val viewModel: ActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = actorAdapter
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
        }
    }
}