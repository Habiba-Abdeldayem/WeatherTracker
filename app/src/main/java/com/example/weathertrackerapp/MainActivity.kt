package com.example.weathertrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current.CurrentWeatherScreen
import com.example.weathertrackerapp.ui.current.CurrentWeatherViewModel
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WeatherTrackerAppTheme {
                val permissionViewModel: PermissionViewModel = viewModel()
                    CurrentWeatherScreen(permissionViewModel)
                val currentWeatherViewModel:CurrentWeatherViewModel = viewModel()
                currentWeatherViewModel.loadWeather(Location(30.0962,31.3416))

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
