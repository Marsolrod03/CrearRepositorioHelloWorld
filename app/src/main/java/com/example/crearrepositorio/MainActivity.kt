package com.example.crearrepositorio

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.crearrepositorio.databinding.ActivityMainBinding
import com.example.crearrepositorio.ui.viewModel.MainViewModel
import com.example.crearrepositorio.ui.viewModel.UiState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, FragmentHome())
                .commit()
        }

        val viewModel: MainViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateHome.collect { uiState ->
                    manageUiState(uiState)
                }
            }
        }
    }

    private fun manageUiState(uiState: UiState) {
        when(uiState){
            UiState.Idle -> {

            }
            is UiState.NavigateToFilms -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostFragment, FirstFragment())
                    .addToBackStack(null)
                    .commit()
                viewModel.navigationCompleted()
            }
            is UiState.NavigateToSeries -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostFragment, SecondFragment())
                    .addToBackStack(null)
                    .commit()
                viewModel.navigationCompleted()
            }
            is UiState.NavigateToActors -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.navHostFragment, ThirdFragment())
                    .addToBackStack(null)
                    .commit()
                viewModel.navigationCompleted()
            }

        }
    }
}
