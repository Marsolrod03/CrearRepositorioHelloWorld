package com.example.crearrepositorio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crearrepositorio.databinding.ActivityMainBinding
import com.example.crearrepositorio.features.home.ui.FragmentHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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