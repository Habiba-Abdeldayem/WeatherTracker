package com.example.weathertrackerapp.data.model


// contains real-time weather data for the exact moment the API request was made

data class CurrentConditions (
    val temp:Float,
    val feelslike: Float,
    val humidity: Float,
    val conditions: String,
    val icon: String,
    val windspeed: Float,
    val sunrise: String,
    val sunset: String
)