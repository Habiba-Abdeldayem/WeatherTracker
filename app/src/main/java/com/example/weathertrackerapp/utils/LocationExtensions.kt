package com.example.weathertrackerapp.utils

import android.location.Location as AndroidLocation
import com.example.weathertrackerapp.data.model.Location as AppLocation

fun AndroidLocation.toAppLocation(): AppLocation {
    return AppLocation(
        latitude = this.latitude,
        longitude = this.longitude
    )
}