package com.example.common_ui

import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected var _binding: T? = null

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}