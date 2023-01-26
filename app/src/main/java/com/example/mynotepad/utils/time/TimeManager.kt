package com.example.mynotepad.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeManager {

     fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)


    }

    fun showTime() {
        val counter = 0
    }

    fun stopTimer(){

    }

}