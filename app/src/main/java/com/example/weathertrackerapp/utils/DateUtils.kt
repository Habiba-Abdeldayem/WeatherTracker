package com.example.weathertrackerapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateUtils {
    fun formatTimestamp(timeStamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd MMM yyyy - HH:mm",Locale.getDefault())
        return simpleDateFormat.format(Date(timeStamp))
    }

    fun formatForecastDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, dd MMM", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date!!)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTo12HourTime(datetime: String): String {
//        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val inputFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
        val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")
        return try {
            val localTime = LocalDateTime.parse(datetime, inputFormat)
            localTime.format(outputFormat)
        } catch (e: Exception) {
            datetime
        }
    }

}