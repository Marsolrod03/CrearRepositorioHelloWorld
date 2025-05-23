package com.example.crearrepositorio

import android.app.Application
import com.example.common.ServiceLocator
import com.example.domain.use_cases.DeleteDatabaseUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MovieDBApplication: Application() {

    @Inject
    lateinit var deleteDatabaseUseCase: DeleteDatabaseUseCase
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this

        applicationScope.launch {
            deleteDatabaseUseCase
        }
    }
}