package com.example.crearrepositorio

import android.app.Application
import com.example.crearrepositorio.common.ServiceLocator
import com.example.crearrepositorio.features.series.data.database.dao.PaginationSeriesDao
import com.example.crearrepositorio.features.series.data.database.dao.SeriesDao
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import java.util.Calendar

@HiltAndroidApp
class MovieDBApplication : Application() {
    @Inject
    lateinit var seriesDao: SeriesDao
    @Inject
    lateinit var paginationSeriesDao: PaginationSeriesDao
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.applicationContext = this
        checkAndPerformWeeklReset()
    }

    private fun checkAndPerformWeeklReset() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val lastResetTime = sharedPreferences.getLong("lastResetTime", 0L)

        val now = Calendar.getInstance()
        val lastMondayAM = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            while (get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                add(Calendar.DAY_OF_MONTH, -1)

            }

            val nowAt8AM = Calendar.getInstance().apply { 
                set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            if(now.timeInMillis < nowAt8AM){
                add(Calendar.WEEK_OF_YEAR, -1)
            }
        }.timeInMillis

        if(lastResetTime < lastMondayAM){
            applicationScope.launch {
                seriesDao.clearSeries()
                paginationSeriesDao.updatePaginationSeries(0)
                sharedPreferences.edit().putLong("lastResetTime", now.timeInMillis).apply()
            }
        }
    }
}