package com.example.weathertrackerapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weathertrackerapp.ui.MainEntryScreen
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherScreen
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.error.NoInternetScreen
import com.example.weathertrackerapp.ui.error.PermissionDeniedScreen
import com.example.weathertrackerapp.ui.forecast.ForecastScreen
import com.example.weathertrackerapp.ui.forecast.ForecastViewModel

sealed class Screen(val route: String) {
    object CurrentWeather : Screen("current_weather_screen")
    object Forecast : Screen("forecast_screen")
    object PermissionDenied : Screen("permission_denied_screen")
    object NoInternet : Screen("no_internet_screen")
    object MainEntryScreen : Screen("main_entry_screen")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    currentWeatherViewModel: CurrentWeatherViewModel,
    permissionViewModel: PermissionViewModel,
    forecastViewModel: ForecastViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainEntryScreen.route
    ) {
        composable(Screen.MainEntryScreen.route) {
            MainEntryScreen(
                navController = navController,
                permissionViewModel = permissionViewModel,
                currentWeatherViewModel = currentWeatherViewModel
            )
        }

        composable(Screen.PermissionDenied.route) {
            PermissionDeniedScreen(navController)
        }

        composable(Screen.NoInternet.route) {
            NoInternetScreen({})
        }

        composable(Screen.CurrentWeather.route) {
            CurrentWeatherScreen(
                permissionViewModel = permissionViewModel,
                currentWeatherViewModel = currentWeatherViewModel,
                navController = navController
            )
        }

        composable(Screen.Forecast.route) {
            ForecastScreen(
                forecastViewModel = forecastViewModel,
                permissionViewModel = permissionViewModel
            )
        }
    }
}
