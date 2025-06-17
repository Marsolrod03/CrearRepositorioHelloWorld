package com.example.crearrepositorio

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.data.workers.HiltWorkerFactoryEntryPoint
import com.example.data.workers.NotificationWorker
import com.example.data.workers.WorkScheduler
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MovieDBApplication : Application(), Configuration.Provider {

//    @Inject
//    lateinit var workManagerConfigProvider: WorkManagerConfigProvider

//    override val workManagerConfiguration: Configuration =
//        workManagerConfigProvider.workManagerConfiguration

    override val workManagerConfiguration: Configuration =
        Configuration.Builder()
            .setWorkerFactory(EntryPoints.get(this, HiltWorkerFactoryEntryPoint::class.java).workerFactory())
            .build()

    override fun onCreate() {
        super.onCreate()
        oneTimeNotification(applicationContext)

        WorkScheduler.workManagerDaily(applicationContext)
    }

    private fun getDelay(hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        return target.timeInMillis - now.timeInMillis
    }

    private fun oneTimeNotification(context: Context) {
        val delayMillis = getDelay(13, 12)

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
            .addTag("notification")
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "unique_notification",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}