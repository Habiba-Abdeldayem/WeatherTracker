package com.example.weathertrackerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.current_weather.RequestLocationPermissionHandler
import com.example.weathertrackerapp.utils.NetworkUtils

@Composable
fun MainEntryScreen(
    navController: NavController,
    permissionViewModel: PermissionViewModel,
    currentWeatherViewModel: CurrentWeatherViewModel
) {
    RequestLocationPermissionHandler(
        permissionViewModel = permissionViewModel,
        currentWeatherViewModel = currentWeatherViewModel,
        navController = navController
    )
}
