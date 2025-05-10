package com.example.weathertrackerapp.ui.current_weather

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weathertrackerapp.R
import com.example.weathertrackerapp.data.model.ApiResponse
import com.example.weathertrackerapp.data.model.CurrentWeather
import com.example.weathertrackerapp.data.model.WeatherResponse
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.navigation.Screen
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme
import com.example.weathertrackerapp.utils.DateUtils.formatTimestamp
import com.example.weathertrackerapp.utils.DateUtils.formatTo12HourTime
import com.example.weathertrackerapp.utils.LocationHelper
import com.example.weathertrackerapp.utils.getWeatherIcon


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeatherScreen(
    permissionViewModel: PermissionViewModel,
    currentWeatherViewModel: CurrentWeatherViewModel,
    navController: NavController,
    onNavigateToForecast: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Scaffold(topBar = { WeatherTopBar() }) { padding ->
//        RequestLocationPermissionHandler(permissionViewModel, currentWeatherViewModel,navController)
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val currentWeatherState by currentWeatherViewModel.weather.observeAsState()

            when (val state = currentWeatherState) {
                null, is ApiResponse.Loading -> {
                    CircularProgressIndicator()
                }

                is ApiResponse.Success<WeatherResponse> -> {
                    CurrentWeatherContent(state.data.currentWeather)
                    val formattedTime = formatTimestamp(state.data.lastUpdated)
                    Text("Last updated: $formattedTime")

                }

                is ApiResponse.Error -> {
                    Log.d("debug", "Error: ${state.message}")
                }
            }
            Spacer(modifier.height(dimensionResource(R.dimen.small_spacing)))
            Button(onClick = {
//                LocationHelper.getLastKnownLocation(context)
//                    ?.let { currentWeatherViewModel.loadWeather(location = it) }
            }) { Text(text = stringResource(R.string.refresh)) }
            Spacer(modifier.height(dimensionResource(R.dimen.small_spacing)))
            Button(onClick = {
                navController.navigate(Screen.Forecast.route)
            }) { Text(text = stringResource(R.string.navigate_to_forecast)) }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopBar() {
    CenterAlignedTopAppBar(
        title = { Text("Current Weather", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeatherContent(
    weather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.big_spacing))
        ) {
            val iconName = weather.icon.replace("-", "_")  // make it match with resource names
            val iconRes = getWeatherIcon(iconName)
            Image(
                painter = painterResource(iconRes),
                contentDescription = iconName,
            )
            Spacer(Modifier.width(24.dp))
            Column {
            Text(text = weather.temp.toString(), style = MaterialTheme.typography.displayLarge)
             Text(text = stringResource(R.string.feels_like, weather.feelslike.toString()), style = MaterialTheme.typography.bodyMedium)
            Text(text = weather.conditions, style = MaterialTheme.typography.bodyLarge)
            }

        }
        Row{
            InfoCard(infoName = stringResource(R.string.humidity), infoValue = stringResource(R.string.humidity_value,weather.humidity.toString()))
            Spacer(Modifier.width(dimensionResource(R.dimen.big_spacing)))
            InfoCard(infoName = stringResource(R.string.wind_speed), infoValue = stringResource(R.string.wind_speed_value,weather.windspeed))
        }
        Spacer(Modifier.height(dimensionResource(R.dimen.small_spacing)))
        Row{
            InfoCard(infoName = stringResource(R.string.sunrise), infoValue = formatTo12HourTime(stringResource(R.string.sunrise_value,weather.sunrise)))
            Spacer(Modifier.width(dimensionResource(R.dimen.big_spacing)))
            InfoCard(infoName = stringResource(R.string.sunset), infoValue = stringResource(R.string.sunset_value,weather.sunset))
        }
    }
}


@Composable
fun InfoCard(infoName: String, infoValue: String, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.small_spacing))
        ) {
            Text(
                text = infoName, fontSize = 20.sp, fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.small_spacing)))
            Text(
                text = infoValue, fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentWeatherScreenPreview() {
    WeatherTrackerAppTheme {
//        CurrentWeatherScreen()
    }
}