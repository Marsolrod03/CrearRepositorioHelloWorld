package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crearrepositorio.R
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import domain.ActorModel
import com.example.crearrepositorio.ui.adapter.ActorAdapter
import com.example.crearrepositorio.ui.back

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    private lateinit var actorAdapter: ActorAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {back()}

        recyclerView = binding.recyclerViewList
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        actorAdapter = ActorAdapter(createList())
        recyclerView.adapter = actorAdapter

        return binding.root
    }

    private fun createList(): List<ActorModel> {
        val actorList = listOf(
            ActorModel("Leonardo DiCaprio", R.drawable.ic_launcher_background),
            ActorModel("Leonardo DiCaprio", R.drawable.ic_launcher_background),
            ActorModel("Leonardo DiCaprio", R.drawable.ic_launcher_background),
            ActorModel("Leonardo DiCaprio", R.drawable.ic_launcher_background),
            ActorModel("Leonardo DiCaprio", R.drawable.ic_launcher_background)
        )
        return actorList
    }
}