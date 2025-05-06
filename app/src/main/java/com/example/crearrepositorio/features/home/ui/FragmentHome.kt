package com.example.crearrepositorio.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.crearrepositorio.common_ui.BaseFragment
import com.example.crearrepositorio.common_ui.ErrorFragment
import com.example.crearrepositorio.common_ui.LoaderController
import com.example.crearrepositorio.common_ui.replaceFragment
import com.example.crearrepositorio.databinding.FragmentHomeBinding
import com.example.crearrepositorio.features.actors.ui.ThirdFragment
import com.example.crearrepositorio.features.films.ui.fragment.FirstFragment
import com.example.crearrepositorio.features.series.ui.SecondFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                (activity as? LoaderController)?.hideLoading()
            }
            is UiState.Navigation.NavigateToFilms -> {
                (activity as? LoaderController)?.hideLoading()
                changeFragment(FirstFragment())
            }
            is UiState.Navigation.NavigateToSeries -> {
                (activity as? LoaderController)?.hideLoading()
                changeFragment(SecondFragment())
            }
            is UiState.Navigation.NavigateToActors -> {
                (activity as? LoaderController)?.hideLoading()
                changeFragment(ThirdFragment())
            }
        }
    }

    private fun changeFragment(fragment: Fragment){
        this.replaceFragment(fragment)
        viewModel.navigationCompleted()
    }

    private fun setClickListeners(){
        binding.button.setOnClickListener { viewModel.navigateToFilms() }
        binding.button2.setOnClickListener { viewModel.navigateToSeries() }
        binding.button3.setOnClickListener { viewModel.navigateToActors() }
    }
}