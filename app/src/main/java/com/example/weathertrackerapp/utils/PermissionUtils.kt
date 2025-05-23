package com.example.weathertrackerapp.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun isPermissionGranted(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun shouldShowRationale(context: Context, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            (context as Activity), permission
        )
    }

}