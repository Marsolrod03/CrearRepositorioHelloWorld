package com.example.crearrepositorio.features.films.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.common_ui.back
import com.example.crearrepositorio.common_ui.BaseFragment

class FirstFragment : BaseFragment<FragmentFirstBinding>() {
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {
            back()
        }
        val view = binding.root
        return view
    }
}