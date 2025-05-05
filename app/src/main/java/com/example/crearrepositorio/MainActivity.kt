package com.example.crearrepositorio

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.crearrepositorio.common_ui.LoaderController
import com.example.crearrepositorio.databinding.ActivityMainBinding
import com.example.crearrepositorio.features.home.ui.FragmentHome
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , LoaderController {
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

    override fun showLoading() {
        binding.loadingContainer.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.loadingContainer.visibility = View.GONE
    }
}