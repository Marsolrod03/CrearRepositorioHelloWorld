package com.example.crearrepositorio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.crearrepositorio.databinding.FragmentFirstBinding
import com.example.crearrepositorio.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_secondFragment_to_fragmentHome)
        }
        val view = binding.root
        return view
    }

}