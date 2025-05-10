package com.example.weathertrackerapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.current_weather.RequestLocationPermissionHandler

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
