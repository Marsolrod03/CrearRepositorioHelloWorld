package com.example.crearrepositorio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.crearrepositorio.databinding.ActivityMainBinding
import com.example.crearrepositorio.features.films.domain.use_case.DeleteDatabaseUseCase
import com.example.crearrepositorio.features.home.ui.FragmentHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var deleteDatabaseUseCase: DeleteDatabaseUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            deleteDatabaseUseCase()
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        if (savedInstanceState==null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment,
                    FragmentHome()
                )
                .commit()
        }

    }
}