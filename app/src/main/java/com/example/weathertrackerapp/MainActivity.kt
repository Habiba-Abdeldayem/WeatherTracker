package com.example.weathertrackerapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.weathertrackerapp.di.ViewModelFactory
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.forecast.ForecastViewModel
import com.example.weathertrackerapp.ui.navigation.WeatherNavGraph
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme

class MainActivity : ComponentActivity() {

    lateinit var permissionViewModel: PermissionViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            WeatherTrackerAppTheme {
                val factory = ViewModelFactory(application)
                val currentWeatherViewModel =
                    ViewModelProvider(this, factory)[CurrentWeatherViewModel::class.java]
                val forecastViewModel =
                    ViewModelProvider(this, factory)[ForecastViewModel::class.java]
                permissionViewModel = viewModel()
                val navController = rememberNavController()
                WeatherNavGraph(
                    navController = navController,
                    currentWeatherViewModel = currentWeatherViewModel,
                    permissionViewModel = permissionViewModel,
                    forecastViewModel = forecastViewModel
                )
            }

        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTrackerAppTheme {

    }
}
