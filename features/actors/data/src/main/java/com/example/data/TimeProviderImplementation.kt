package com.example.data

import com.example.domain.TimeProvider
import java.util.Calendar
import javax.inject.Inject

class TimeProviderImplementation @Inject constructor() : TimeProvider {
    override fun getCurrentCalendar(): Calendar {
        return Calendar.getInstance()
    }

    override fun getCurrentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}