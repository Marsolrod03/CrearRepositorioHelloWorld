package com.example.crearrepositorio.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.crearrepositorio.databinding.FragmentSecondBinding
import com.example.crearrepositorio.ui.back

class SecondFragment : BaseFragment<FragmentSecondBinding>() {
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        binding.btnHome.setOnClickListener {back()}
        return binding.root
    }

}