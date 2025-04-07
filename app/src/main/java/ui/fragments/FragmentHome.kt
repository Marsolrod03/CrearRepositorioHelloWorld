package ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.crearrepositorio.databinding.FragmentHomeBinding
import ui.replaceFragment
import ui.viewModel.MainViewModel
import ui.viewModel.UiState
import kotlinx.coroutines.launch

class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

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
            }
            is UiState.Navigation.NavigateToFilms -> {
                changeFragment(FirstFragment())
            }
            is UiState.Navigation.NavigateToSeries -> {
                changeFragment(SecondFragment())
            }
            is UiState.Navigation.NavigateToActors -> {
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