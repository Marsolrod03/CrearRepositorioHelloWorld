package com.example.crearrepositorio

import android.app.Application
import com.example.crearrepositorio.common.ServiceLocator
import com.example.crearrepositorio.features.films.domain.use_case.DeleteDatabaseUseCase
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject

@HiltAndroidApp
class MovieDBApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this
    }
}