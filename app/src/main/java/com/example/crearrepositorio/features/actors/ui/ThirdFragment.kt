package com.example.crearrepositorio.features.actors.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import com.example.crearrepositorio.common_ui.back
import com.example.crearrepositorio.common_ui.BaseFragment

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener { back()}
        return binding.root
    }
}