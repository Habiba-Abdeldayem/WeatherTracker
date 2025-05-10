package com.example.weathertrackerapp.utils

import androidx.annotation.DrawableRes
import com.example.weathertrackerapp.R

@DrawableRes
fun getWeatherIcon(iconName: String): Int {
    return when (iconName) {
        "clear_day" -> R.drawable.clear
        "cloudy" -> R.drawable.cloudy
        "fog" -> R.drawable.fog
        "hail" -> R.drawable.hail
        "partly_cloudy_day" -> R.drawable.partly_cloudy_day
        "rain" -> R.drawable.rain
        "rain_snow" -> R.drawable.rain_snow
        "sleet" -> R.drawable.sleet
        "snow" -> R.drawable.snow
        "thunder" -> R.drawable.thunder
        "thunder_rain" -> R.drawable.thunder_rain
        "wind" -> R.drawable.wind
        else -> R.drawable.clear // fallback icon
    }
}
