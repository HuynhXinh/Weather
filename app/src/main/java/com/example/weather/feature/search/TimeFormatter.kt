package com.example.weather.feature.search

import java.text.SimpleDateFormat
import java.util.*

interface TimeFormatter {
    fun format(dt: Long): String
}

class TimeFormatterImpl : TimeFormatter {
    companion object {
        const val FORMAT_DATE = "EEE, dd MMM yyyy"
    }

    override fun format(dt: Long): String {
        val format = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
        return format.format(dt)
    }
}