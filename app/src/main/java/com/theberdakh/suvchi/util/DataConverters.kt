package com.theberdakh.suvchi.util

import java.text.SimpleDateFormat
import java.util.Locale

fun convertDateTime(time: String, currentPattern: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", newPattern: String = "HH:mm, dd-MM-yyyy" ): String {
    val inputFormat = SimpleDateFormat(currentPattern, Locale.getDefault())
    val parsedDate = inputFormat.parse(time)
    val outputFormat = SimpleDateFormat(newPattern, Locale.getDefault())
    return outputFormat.format(parsedDate)
}

fun convertSecondToHourAndMinutes(seconds: Double): Pair<Int, Int> {

        val hours = (seconds / 3600).toInt()
        val minutes = ((seconds % 3600) / 60).toInt()
        return Pair(hours, minutes)
}