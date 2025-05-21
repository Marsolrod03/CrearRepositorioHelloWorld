package com.example.crearrepositorio

import android.app.Application
import com.example.domain.use_cases.DeleteDatabaseUseCase
import com.example.lib.common.ServiceLocator
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MovieDBApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this
    }
}