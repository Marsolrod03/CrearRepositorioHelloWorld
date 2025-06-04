package com.example.crearrepositorio

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.workers.DeleteDatabaseWorker
import com.example.data.workers.WorkScheduler
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovieDBApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface HiltWorkerFactoryEntryPoint {
        fun workerFactory(): HiltWorkerFactory
    }

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setWorkerFactory(EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory())
            .build()


    override fun onCreate() {
        super.onCreate()

//        scheduleOneTimeDeleteDatabaseWork()

        WorkScheduler.workManagerDaily(applicationContext)
    }

    private fun scheduleOneTimeDeleteDatabaseWork() {
        val tag = "DeleteDataBase"

        val now = Calendar.getInstance()
        val time = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val initialDelay = time.timeInMillis - now.timeInMillis

        val deleteWorkRequest = OneTimeWorkRequestBuilder<DeleteDatabaseWorker>()
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .addTag(tag)
            .build()

        WorkManager.getInstance(this).enqueue(deleteWorkRequest)
        //envia solicitud de trabajo y enqueue la pone a la cola
    }
}