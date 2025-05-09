package com.example.weathertrackerapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
contains day-by-day forecasts (including today). Each day object has:
- Summary data for the whole day (max/min temp, etc.)
- An hours array with hourly breakdowns
*/
@Serializable
data class Forecast(
    val datetime: String,
    @SerialName("tempmax")val tempMax: Float,
    @SerialName("tempmin")val tempMin: Float,
    val icon: String,
    val conditions: String
)