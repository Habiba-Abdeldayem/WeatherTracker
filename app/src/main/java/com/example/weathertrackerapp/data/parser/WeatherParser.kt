package com.example.weathertrackerapp.data.parser

import com.example.weathertrackerapp.data.model.CurrentWeather
import com.example.weathertrackerapp.data.model.ForecastDay
import com.example.weathertrackerapp.data.model.WeatherResponse
import org.json.JSONArray
import org.json.JSONObject

object WeatherParser {
    fun parse(jsonString: String): WeatherResponse {
        return try {
            val json = JSONObject(jsonString)
            WeatherResponse(
                currentWeather = parseCurrentConditions(json.getJSONObject("currentConditions")),
                days = parseDays(json.getJSONArray("days"))
            )
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid JSON: ${e.message}")
        }

    }

    private fun parseCurrentConditions(json:JSONObject): CurrentWeather {
        return CurrentWeather(
            temp = json.getDouble("temp").toFloat(),
            feelslike = json.getDouble("feelslike").toFloat(),
            conditions = json.getString("conditions"),
            windspeed =json.getDouble("windspeed").toFloat(),
            humidity = json.getDouble("humidity").toFloat(),
            icon = json.getString("icon"),
            sunset = json.getString("sunset"),
            sunrise = json.getString("sunrise")
        )
    }

    private fun parseDays(jsonArray: JSONArray): List<ForecastDay> {
        val days = mutableListOf<ForecastDay>()
        val daysCount = minOf(5, jsonArray.length())
        for(i in 0 until daysCount) {
            val day = jsonArray.getJSONObject(i)
            days.add(ForecastDay(
                datetime = day.getString("datetime"),
                tempmax = day.getDouble("tempmax").toFloat(),
                tempmin = day.getDouble("tempmin").toFloat(),
                icon = day.getString("icon"),
                conditions = day.getString("conditions")
            ))
        }
        return days
    }
}