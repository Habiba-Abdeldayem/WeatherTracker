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
import com.example.weathertrackerapp.ui.MainEntryScreen
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherScreen
import com.example.weathertrackerapp.ui.current_weather.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.forecast.ForecastViewModel
import com.example.weathertrackerapp.ui.navigation.WeatherNavGraph
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val factory = ViewModelFactory(application)
            val currentWeatherViewModel  =  ViewModelProvider(this, factory)[CurrentWeatherViewModel::class.java]
            val forecastViewModel  =  ViewModelProvider(this, factory)[ForecastViewModel::class.java]
            val permissionViewModel: PermissionViewModel = viewModel()
            val navController = rememberNavController()
            WeatherNavGraph(
                navController = navController,
                currentWeatherViewModel = currentWeatherViewModel,
                permissionViewModel = permissionViewModel,
                forecastViewModel = forecastViewModel
            )
//            MainEntryScreen(navController,permissionViewModel,currentWeatherViewModel)

            WeatherTrackerAppTheme {
//                CurrentWeatherScreen(permissionViewModel, currentWeatherViewModel)

//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//
//                }
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
