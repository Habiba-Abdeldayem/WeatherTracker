package com.example.weathertrackerapp.ui.current_weather

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathertrackerapp.data.api.WeatherApiClient
import com.example.weathertrackerapp.data.model.ApiResponse
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.data.model.WeatherResponse
import com.example.weathertrackerapp.data.repository.WeatherRepository
import com.example.weathertrackerapp.utils.NetworkUtils
import com.example.weathertrackerapp.utils.WeatherCacheHelper

private const val TAG = "current weather view model"
class CurrentWeatherViewModel(
    application: Application,
    private val repository: WeatherRepository
): AndroidViewModel(application) {
    private val _weather = MutableLiveData<ApiResponse<WeatherResponse>>()
    val weather: LiveData<ApiResponse<WeatherResponse>> = _weather

    fun loadWeather(location: Location) {
        val context = getApplication<Application>().applicationContext
        if(!NetworkUtils.isNetworkAvailable(context)) {
            val cached = WeatherCacheHelper.getCachedWeather(context)
            if(cached != null) {
                _weather.postValue(ApiResponse.Success(cached))
                Toast.makeText(context, "No Internet Connection, Loaded cached data", Toast.LENGTH_SHORT).show()
                Log.d(TAG, cached.toString())
            } else {
                _weather.postValue(ApiResponse.Error("No internet and no cached data available."))
                Toast.makeText(context, "No internet and no cached data available.", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "No internet and no cached data available.")

            }
            return
        }

        _weather.value = ApiResponse.Loading
        repository.getWeather(location, object : WeatherApiClient.WeatherCallback {
            override fun onSuccess(weatherResponse: WeatherResponse) {
                _weather.postValue(ApiResponse.Success(weatherResponse))
                WeatherCacheHelper.saveWeather(context,weatherResponse)
//                Log.d(TAG,weatherResponse.currentConditions.temp.toString())
            }

            override fun onFailure(error: String) {
                _weather.postValue(ApiResponse.Error(error))
            }

        })
    }

    fun hasCachedData(context: Context): Boolean {
        return WeatherCacheHelper.getCachedWeather(context) != null
    }
}