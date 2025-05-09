package com.example.weathertrackerapp.ui.current

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weathertrackerapp.data.api.WeatherApiClient
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.data.model.WeatherResponse
import com.example.weathertrackerapp.data.repository.WeatherRepository
private const val TAG = "current weather view model"
class CurrentWeatherViewModel: ViewModel() {
    private val repository = WeatherRepository()

    fun loadWeather(location: Location) {
        repository.getWeather(location, object : WeatherApiClient.WeatherCallback {
            override fun onSuccess(weather: WeatherResponse) {
                Log.d(TAG, weather.currentConditions.sunrise)
            }

            override fun onFailure(error: String) {
            }

        })
    }
}