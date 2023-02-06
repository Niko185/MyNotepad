package com.example.mynotepad.utils

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.*

object TimeManager {
    const val TIME_FORMAT = "hh:mm - dd/MM/yyyy"

     fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    fun getTimeFormat(timeFormat: String, preference: SharedPreferences): String {

        val defaultFormatter = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
        val defaultDate = defaultFormatter.parse(timeFormat)

        val formatInPrefScreen = preference.getString("key_time_format", TIME_FORMAT)
        val newFormatter = SimpleDateFormat(formatInPrefScreen, Locale.getDefault())
        return if(defaultDate != null) {
            newFormatter.format(defaultDate)
        } else timeFormat
    }
}