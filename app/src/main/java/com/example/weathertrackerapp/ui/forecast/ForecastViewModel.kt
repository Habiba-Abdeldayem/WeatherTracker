package com.example.weathertrackerapp.ui.forecast

import android.app.Application
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathertrackerapp.R
import com.example.weathertrackerapp.data.api.WeatherApiClient
import com.example.weathertrackerapp.data.model.ApiResponse
import com.example.weathertrackerapp.data.model.ForecastDay
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.data.model.WeatherResponse
import com.example.weathertrackerapp.data.repository.WeatherRepository
import com.example.weathertrackerapp.utils.NetworkUtils
import com.example.weathertrackerapp.utils.WeatherCacheHelper


class ForecastViewModel(
    application: Application,
    private val repository: WeatherRepository
): AndroidViewModel(application){
//    val context = getApplication<Application>().applicationContext
    private val _forecastDays = MutableLiveData<ApiResponse<List<ForecastDay>>>()
    val forecastDays: LiveData<ApiResponse<List<ForecastDay>>> = _forecastDays



    fun loadForecast(location:Location) {
        val context = getApplication<Application>().applicationContext

        if(!NetworkUtils.isNetworkAvailable(context)) {
            val cached = WeatherCacheHelper.getCachedForecast(context)
            if(cached != null) {
                _forecastDays.postValue(ApiResponse.Success(cached))
            } else {
                _forecastDays.postValue(ApiResponse.Error(context.getString(R.string.no_internet_and_no_cache)))
            }
        return
        }
        _forecastDays.value = ApiResponse.Loading

        repository.getWeather(location, object:WeatherApiClient.WeatherCallback {
            override fun onSuccess(weatherResponse: WeatherResponse) {
                _forecastDays.postValue(ApiResponse.Success(weatherResponse.days))
                WeatherCacheHelper.saveForecast(context, weatherResponse)
            }

            override fun onFailure(error: String) {
                _forecastDays.postValue(ApiResponse.Error(error))
            }

        })
    }


}