package com.example.weathertrackerapp.data.api

import android.os.Handler
import android.os.Looper
import com.example.weathertrackerapp.BuildConfig
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.data.model.WeatherResponse
import com.example.weathertrackerapp.data.parser.WeatherParser
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class WeatherApiClient {
    interface WeatherCallback {
        fun onSuccess(weather: WeatherResponse)
        fun onFailure(error: String)
    }

    companion object {
        fun getCurrentWeather(location: Location, callback: WeatherCallback) {
            Thread {
                var connection: HttpURLConnection? = null
                try {
                    val apiKey = BuildConfig.WEATHER_API_KEY
                    val url = URL(
                        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/" +
                                "services/timeline/${location.latitude}%2C${location.longitude}" +
                                "?unitGroup=metric" +
                                "&key=${apiKey}" +
                                "&contentType=json"
                    )
                    connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"

                    if(connection.responseCode == HttpURLConnection.HTTP_OK) {
                        val response = connection.inputStream.bufferedReader().use{it.readText()}
                        val weatherData = WeatherParser.parse(response)
                        Handler(Looper.getMainLooper()).post {
                            callback.onSuccess(weatherData)
                        }
                    } else {
                        throw IOException("HTTP error: ${connection.responseCode}")
                    }
                }

                catch (e:Exception) {
                    Handler(Looper.getMainLooper()).post {
                        callback.onFailure(e.message ?: "Unknown Error")
                    }
                }
                finally {
                    connection?.disconnect()
                }
            }.start()
        }

    }
}
