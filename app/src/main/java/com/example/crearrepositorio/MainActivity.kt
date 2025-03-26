package com.example.crearrepositorio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.crearrepositorio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.navHostFragment, FragmentHome())
                .commit()
        }
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

}
