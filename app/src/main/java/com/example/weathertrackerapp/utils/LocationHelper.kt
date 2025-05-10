package com.example.weathertrackerapp.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.weathertrackerapp.data.model.Location

object LocationHelper {
    fun getLastKnownLocation(context: Context): Location? {
        // provider can be gps, network, passive
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)

        // first try GPS (most accurate), if not found try other providers
        for (provider in providers.reversed()) {
            try {
                val location = locationManager.getLastKnownLocation(provider)
                if (location != null) {
                    return Location(
                        location.latitude,
                        location.longitude
                    )
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
        return null
    }
}
