package com.example.home_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.common_ui.BaseFragment
import com.example.common_ui.replaceFragment
import com.example.home_ui.databinding.FragmentHomeBinding
import com.example.home_ui.home_screen.HomeScreen
import com.example.ui.SeriesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHome : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.stateHome.collectAsState()
                HomeScreen(
                    uiState = state,
                    onNavigate = { target ->
                        when (target) {
                            NavigationTarget.FILMS -> {}
                            NavigationTarget.SERIES -> changeFragment(SeriesFragment())
                            NavigationTarget.ACTORS -> {}
                        }

                    },
                    onNavigationComplete = {
                        viewModel.navigationCompleted()
                    }
                )

            }
        }
    }


    private fun changeFragment(fragment: Fragment) {
        this.replaceFragment(fragment)
        viewModel.navigationCompleted()
    }
}

