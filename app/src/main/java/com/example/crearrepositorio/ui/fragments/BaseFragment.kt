package com.example.crearrepositorio.ui.fragments

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.crearrepositorio.databinding.FragmentHomeBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected var _binding: T? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}