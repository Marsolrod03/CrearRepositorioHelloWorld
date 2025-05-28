package com.example.common_ui

import androidx.fragment.app.Fragment

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