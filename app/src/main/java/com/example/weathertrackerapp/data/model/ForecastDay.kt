package com.example.weathertrackerapp.data.model

/*
contains day-by-day forecasts (including today). Each day object has:
- Summary data for the whole day (max/min temp, etc.)
- An hours array with hourly breakdowns
*/

data class ForecastDay(
    val datetime: String,
    val tempmax: Float,
    val tempmin: Float,
    val icon: String,
    val conditions: String
)