package com.example.data.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object WorkScheduler {

    fun workManagerDaily(context: Context) {
        val localHourZone = TimeZone.getDefault()
        val now = Calendar.getInstance(localHourZone)

        val calendar = Calendar.getInstance(localHourZone).apply {
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 50)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (before(now)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        val delayInitial = calendar.timeInMillis - System.currentTimeMillis()

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            1, TimeUnit.DAYS,
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(delayInitial, TimeUnit.MILLISECONDS)
            .addTag("daily_8am_notification")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "daily_notification_at_8am",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}