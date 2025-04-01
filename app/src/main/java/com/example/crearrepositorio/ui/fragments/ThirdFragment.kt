package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentThirdBinding
import com.example.crearrepositorio.ui.back

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {back()}
        return binding.root
    }
}