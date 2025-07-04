package com.example.ui

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResourceProvider@Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }
}