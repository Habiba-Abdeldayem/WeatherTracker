package com.example.weathertrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current.CurrentWeatherScreen
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WeatherTrackerAppTheme {
                val permissionViewModel: PermissionViewModel = viewModel()
                    CurrentWeatherScreen(permissionViewModel)
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
