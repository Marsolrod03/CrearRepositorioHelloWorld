package com.example.data.workers

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ResetDatabaseScheduler {

    fun scheduleWeeklyResetWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ResetDatabaseWorker>(
            1, TimeUnit.DAYS
        )
            .addTag("WeeklyDatabaseReset_WorkRequest")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "WeeklyDatabaseReset_WorkManager",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}