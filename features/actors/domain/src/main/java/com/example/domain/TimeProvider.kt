package com.example.domain

import java.util.Calendar

interface TimeProvider {
    fun getCurrentCalendar(): Calendar
    fun getCurrentTimeMillis(): Long
}