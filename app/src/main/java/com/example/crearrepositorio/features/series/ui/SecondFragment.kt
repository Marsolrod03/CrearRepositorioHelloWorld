package com.example.crearrepositorio.features.series.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import com.example.crearrepositorio.common_ui.back
import com.example.crearrepositorio.common_ui.BaseFragment

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener { back()}
        return binding.root
    }

}