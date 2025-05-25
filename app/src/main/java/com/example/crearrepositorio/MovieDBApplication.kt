package com.example.crearrepositorio

import android.app.Application
import com.example.domain.use_case.DeleteDatabaseUseCase
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class MovieDBApplication: Application() {
    @Inject
    lateinit var deleteDatabaseUseCase: DeleteDatabaseUseCase

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch{
            deleteDatabaseUseCase()
        }
        //diferencia entre Dispaters.io/ y los demas (y cual está prohibido)
    }
}