package com.example.weathertrackerapp.data.model

data class WeatherResponse(
    val currentWeather: CurrentWeather,
    val days: List<ForecastDay>,
    val lastUpdated: Long = 0L
)
