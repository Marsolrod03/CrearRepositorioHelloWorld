package com.example.lib.common_ui

import androidx.fragment.app.Fragment
import com.example.crearrepositorio.R

fun Fragment.replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.navHostFragment, fragment)
        .addToBackStack("CambioDeFragment")
        .commit()
}

fun Fragment.back(){parentFragmentManager.popBackStack()}