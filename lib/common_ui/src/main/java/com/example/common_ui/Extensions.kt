package com.example.common_ui

import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(fragment: Fragment) {
    val containerId = (activity as FragmentContainerProvider).getFragmentContainerId()

    parentFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack("CambioDeFragment")
        .commit()
}

fun Fragment.back(){parentFragmentManager.popBackStack()}