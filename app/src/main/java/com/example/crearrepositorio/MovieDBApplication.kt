package com.example.crearrepositorio

import android.app.Application
import android.content.Context
import com.example.crearrepositorio.common.ServiceLocator
import com.example.crearrepositorio.features.actors.data.database.dao.ActorDao
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import androidx.core.content.edit
import com.example.crearrepositorio.features.actors.data.database.dao.PaginationActorsDao

@HiltAndroidApp
class MovieDBApplication: Application() {
    @Inject lateinit var actorsDao: ActorDao
    @Inject lateinit var paginationDao: PaginationActorsDao
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this
        checkAndPerformWeeklyDeletion()
    }

    private fun checkAndPerformWeeklyDeletion() {
        val sharedPreferences = getSharedPreferences(
            packageName + "_preferences",
            Context.MODE_PRIVATE
        )
        val lastDeletionTimeMillis = sharedPreferences.getLong("last_actor_deletion_time", 0L)

        val now = Calendar.getInstance()
        val lastMonday8Am = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_WEEK, -1)
            }

            val nowAt8AmThisMonday = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            if (now.timeInMillis < nowAt8AmThisMonday) {
                add(Calendar.WEEK_OF_YEAR, -1)
            }
        }.timeInMillis

        if (lastDeletionTimeMillis < lastMonday8Am) {
            applicationScope.launch {
                actorsDao.deleteAllActors()
                paginationDao.updateLastLoadedPage(0)
                sharedPreferences.edit{
                    putLong("last_actor_deletion_time", System.currentTimeMillis())
                }
            }
        }
    }
}