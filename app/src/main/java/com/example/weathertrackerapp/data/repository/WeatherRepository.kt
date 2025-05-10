package com.example.weathertrackerapp.data.repository

import com.example.weathertrackerapp.data.api.WeatherApiClient
import com.example.weathertrackerapp.data.api.WeatherApiClient.WeatherCallback
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.data.model.WeatherResponse

class WeatherRepository {

    fun getWeather(location: Location, callback: WeatherCallback) {
        WeatherApiClient.getCurrentWeather(location, object : WeatherCallback {

            override fun onSuccess(weatherResponse: WeatherResponse) {
                callback.onSuccess(weatherResponse)
            }

            override fun onFailure(error: String) {
                callback.onFailure(error)
            }
        })
    }
}