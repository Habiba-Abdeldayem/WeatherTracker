package com.example.weathertrackerapp.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathertrackerapp.data.repository.WeatherRepository
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.forecast.ForecastViewModel

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    private val repository = WeatherRepository()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java) -> {
                CurrentWeatherViewModel(application, repository) as T
            }

            modelClass.isAssignableFrom(ForecastViewModel::class.java) -> {
                ForecastViewModel(application, repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

}