package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import com.example.crearrepositorio.ui.adapter.ActorAdapter
import com.example.crearrepositorio.ui.back
import com.example.crearrepositorio.ui.viewModel.ActorViewModel
import kotlinx.coroutines.launch

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: ActorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {back()}
        recyclerView = binding.recyclerViewList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.actorList.collect { actorList ->
                actorAdapter = ActorAdapter(actorList)
                recyclerView.adapter = actorAdapter
            }
        }
        return binding.root
    }
}