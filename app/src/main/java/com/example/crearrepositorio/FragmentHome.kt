package com.example.crearrepositorio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.crearrepositorio.databinding.FragmentHomeBinding
import com.example.crearrepositorio.ui.viewModel.MainViewModel
import com.example.crearrepositorio.ui.viewModel.UiState
import kotlinx.coroutines.launch

class FragmentHome : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateHome.collect { uiState ->
                    manageUiState(uiState)
                }
            }
        }
        setClickListeners()
    }

    private fun manageUiState(uiState: UiState) {
        when (uiState) {
            UiState.Idle -> {
            }
            is UiState.NavigateToFilms -> {
                changeFragment(FirstFragment())
            }
            is UiState.NavigateToSeries -> {
                changeFragment(SecondFragment())
            }
            is UiState.NavigateToActors -> {
                changeFragment(ThirdFragment())
            }

        }
    }

    private fun changeFragment(fragment: Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .addToBackStack(null)
            .commit()
        viewModel.navigationCompleted()
    }

    private fun setClickListeners(){
        binding.button.setOnClickListener { viewModel.navigateToFilms() }
        binding.button2.setOnClickListener { viewModel.navigateToSeries() }
        binding.button3.setOnClickListener { viewModel.navigateToActors() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}