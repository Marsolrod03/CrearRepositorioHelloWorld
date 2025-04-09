package com.example.crearrepositorio.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.crearrepositorio.R
import com.example.crearrepositorio.databinding.ActivityMainBinding

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
                    com.example.crearrepositorio.ui.fragments.FragmentHome()
                )
                .commit()
        }
    }
}
