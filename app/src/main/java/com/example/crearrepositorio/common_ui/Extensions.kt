package com.example.crearrepositorio.common_ui

import androidx.fragment.app.Fragment
import com.example.crearrepositorio.R

fun Fragment.replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.navHostFragment, fragment)
        .addToBackStack("CambioDeFragment")
        .commit()
}

fun Fragment.replaceFragmentWithoutBackStack(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.navHostFragment, fragment)
        .commit()
}

fun Fragment.back(){parentFragmentManager.popBackStack()}