package com.example.crearrepositorio

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun Fragment.replaceFragment(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.navHostFragment, fragment)
        .addToBackStack("CambioDeFragment")
        .commit()
}



fun Fragment.backHome(){
    parentFragmentManager.popBackStack()
}