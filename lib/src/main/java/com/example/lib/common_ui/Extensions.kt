package com.example.lib.common_ui

import androidx.fragment.app.Fragment

fun Fragment.replaceFragment(fragment: Fragment) {
    val activity = activity ?: throw IllegalStateException(
        "Fragment must be attached to a FragmentActivity that implements FragmentContainerProvider"
    )
    val containerId = (activity as FragmentContainerProvider).getFragmentContainerId()

    parentFragmentManager.beginTransaction()
        .replace(containerId, fragment)
        .addToBackStack("CambioDeFragment")
        .commit()
}

fun Fragment.back(){parentFragmentManager.popBackStack()}