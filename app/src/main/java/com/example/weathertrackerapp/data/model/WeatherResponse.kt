package com.example.weathertrackerapp.data.model

data class WeatherResponse(
    val currentConditions: CurrentConditions,
    val days: List<ForecastDay>
)