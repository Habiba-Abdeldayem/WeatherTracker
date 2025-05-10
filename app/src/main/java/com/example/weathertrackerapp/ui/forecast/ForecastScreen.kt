package com.example.weathertrackerapp.ui.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathertrackerapp.R
import com.example.weathertrackerapp.data.model.ApiResponse
import com.example.weathertrackerapp.data.model.ForecastDay
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.current_weather.WeatherTopBar
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme
import com.example.weathertrackerapp.utils.DateUtils
import com.example.weathertrackerapp.utils.DateUtils.formatTo12HourTime
import com.example.weathertrackerapp.utils.LocationHelper
import com.example.weathertrackerapp.utils.getWeatherIcon

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(
    forecastViewModel: ForecastViewModel,
    permissionViewModel: PermissionViewModel
) {
    val forecastState by forecastViewModel.forecastDays.observeAsState()
    val locationPermissionState by permissionViewModel.locationPermissionState
    val context = LocalContext.current
    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState) {
            val androidLocation = LocationHelper.getLastKnownLocation(context)
            val location = androidLocation ?: Location(30.096, 31.34)
            forecastViewModel.loadForecast(location)
        }
    }
    Scaffold(
        topBar = { WeatherTopBar() }
    ) { padding ->
        when (val state = forecastState) {
            is ApiResponse.Success -> {
                val days = state.data
                LazyColumn(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(days) { day ->
                        DayCard(day)
                    }
                }
            }

            is ApiResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is ApiResponse.Error -> {
                Text(text = "Error loading forecast", modifier = Modifier.padding(padding))
            }

            null -> {}
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(
    day: ForecastDay,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        val iconName = day.icon.replace("-", "_")  // make it match with resource names
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = DateUtils.formatForecastDate(day.datetime),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(4.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val icon = getWeatherIcon(day.icon.replace("-", "_"))
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = iconName,modifier=Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = day.conditions,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.max_forecast,day.tempmax.toString()),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.min_forecast,day.tempmin.toString()),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                }
            }


        }
    }
}



@Preview(showBackground = true)
@Composable
fun ForecastScreenPreview() {
    WeatherTrackerAppTheme {
//        DayCard()
//        CurrentWeatherScreen()
    }
}