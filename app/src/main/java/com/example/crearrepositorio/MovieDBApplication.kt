package com.example.crearrepositorio

import android.app.Application
import com.example.crearrepositorio.common.ServiceLocator

class MovieDBApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this
    }
}