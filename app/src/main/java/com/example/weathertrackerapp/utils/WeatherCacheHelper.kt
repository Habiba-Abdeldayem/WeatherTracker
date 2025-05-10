package com.example.weathertrackerapp.utils

import android.content.Context
import android.util.Log
import com.example.weathertrackerapp.data.model.CurrentWeather
import com.example.weathertrackerapp.data.model.ForecastDay
import com.example.weathertrackerapp.data.model.WeatherResponse
import org.json.JSONArray
import org.json.JSONObject

object WeatherCacheHelper {
    private const val PREF_NAME = "weather_prefs"
    private const val CURRENT_WEATHER_KEY = "current_weather"
    private const val FORECAST_KEY = "forecast_weather"
    private const val LAST_UPDATED_KEY = "last_updated"
//    put("last_updated", System.currentTimeMillis())

    fun saveAllWeather(context: Context, weatherResponse: WeatherResponse) {
        saveWeather(context,weatherResponse)
        saveForecast(context,weatherResponse)

    }
    fun saveWeather(context: Context, weatherResponse: WeatherResponse) {
        val currentWeather = weatherResponse.currentWeather
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = JSONObject().apply {
            put("temp",currentWeather.temp)
            put("feelslike",currentWeather.feelslike)
            put("humidity",currentWeather.humidity)
            put("conditions",currentWeather.conditions)
            put("icon",currentWeather.icon)
            put("windspeed",currentWeather.windspeed)
            put("sunrise",currentWeather.sunrise)
            put("sunset",currentWeather.sunset)
        }

        editor.putString(CURRENT_WEATHER_KEY,json.toString()).apply()
        editor.putLong(LAST_UPDATED_KEY,System.currentTimeMillis()).apply()
        Log.d("prefs_debug", "Saved current weather: $json")
    }

    fun getCachedWeather(context: Context): WeatherResponse? {
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val jsonString = prefs.getString(CURRENT_WEATHER_KEY,null) ?: return null

        return try {
            val json = JSONObject(jsonString)
            val temp = json.getDouble("temp").toFloat()
            val feelslike = json.getDouble("feelslike").toFloat()
            val humidity = json.getDouble("humidity").toFloat()
            val conditions = json.getString("conditions")
            val icon = json.getString("icon")
            val windspeed = json.getDouble("windspeed").toFloat()
            val sunrise = json.getString("sunrise")
            val sunset = json.getString("sunset")
            val lastUpdated = prefs.getLong(LAST_UPDATED_KEY, 0L)

            val currentWeather = CurrentWeather(
                temp, feelslike, humidity, conditions, icon, windspeed, sunrise, sunset
            )

            val forecast = getCachedForecast(context) ?: emptyList()

            WeatherResponse(
                currentWeather = currentWeather,
                days = forecast,
                lastUpdated = lastUpdated
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveForecast(context: Context, weatherResponse: WeatherResponse) {
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val forecastArray = JSONArray()
        weatherResponse.days.forEach{day ->
            val dayJson = JSONObject().apply {
                put("datetime",day.datetime)
                put("tempmax",day.tempmax)
                put("tempmin",day.tempmin)
                put("icon",day.icon)
                put("conditions",day.conditions)
            }
            forecastArray.put(dayJson)
        }

        val json = JSONObject().apply {
            put("days", forecastArray)
        }

        editor.putString(FORECAST_KEY,json.toString())
        editor.apply()
    }

    fun getCachedForecast(context: Context): List<ForecastDay>? {
        val prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        val jsonString = prefs.getString(FORECAST_KEY,null) ?: return null

        return try {
            val json = JSONObject(jsonString)
            val daysJsonArray = json.getJSONArray("days")

            val daysList = mutableListOf<ForecastDay>()
            for(i in 0 until daysJsonArray.length()) {
                val dayJson = daysJsonArray.getJSONObject(i)
                val day = ForecastDay(
                    datetime = dayJson.getString("datetime") ,
                    tempmax = dayJson.getDouble("tempmax").toFloat(),
                    tempmin = dayJson.getDouble("tempmin").toFloat() ,
                    icon = dayJson.getString("icon"),
                    conditions = dayJson.getString("conditions")
                )
                daysList.add(day)
            }
            daysList
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}