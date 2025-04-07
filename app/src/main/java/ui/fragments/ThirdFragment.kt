package ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import ui.adapter.ActorAdapter
import ui.viewModel.ActorState
import ui.viewModel.ActorViewModel
import kotlinx.coroutines.launch

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private val actorAdapter = ActorAdapter(emptyList())
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerViewList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorList.collect { actorsState ->
                stateHandle(actorsState)
            }
        }
        return binding.root
    }

    private fun stateHandle(actorState: ActorState){
        when (actorState) {
            ActorState.Idle -> {
            }
            is ActorState.Success -> {
                actorAdapter.updateActors(actorState.actors)
                recyclerView.adapter = actorAdapter
            }
        }
    }
}