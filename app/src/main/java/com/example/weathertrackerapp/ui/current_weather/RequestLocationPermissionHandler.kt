package com.example.weathertrackerapp.ui.current_weather

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.weathertrackerapp.data.model.Location
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.utils.LocationHelper
import com.example.weathertrackerapp.utils.PermissionUtils

@Composable
fun RequestLocationPermissionHandler(
    permissionViewModel: PermissionViewModel,
    currentWeatherViewModel: CurrentWeatherViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val showRationalDialog by permissionViewModel.showRationaleDialog
    val locationPermissionState by permissionViewModel.locationPermissionState

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                permissionViewModel.updatePermissionState(true)
            } else {
                if (PermissionUtils.shouldShowRationale(context, permission)) {
                    permissionViewModel.updateShowRational(true)
                } else { // permanently denied
                    navController.navigate("permission_denied_screen") {
                        popUpTo("main_entry_screen") { inclusive = true }
                    }
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(permission)
    }

    if (showRationalDialog) {
        RationalDialog(
            onDismiss = { permissionViewModel.updateShowRational(false) },
            onConfirm = {
                permissionViewModel.updateShowRational(false)
                permissionLauncher.launch(permission)
            }
        )
    }

    LaunchedEffect(locationPermissionState) {
        if (locationPermissionState) {
            val androidLocation = LocationHelper.getLastKnownLocation(context)
            val location = androidLocation ?: Location(30.096, 31.34)
            currentWeatherViewModel.loadWeather(location)

            navController.navigate("current_weather_screen") {
                popUpTo("main_entry_screen") { inclusive = true }
            }
        }
    }
}

//@Composable
//fun RequestLocationPermissionHandler(
//    permissionViewModel: PermissionViewModel,
//    currentWeatherViewModel: CurrentWeatherViewModel
//) {
//    val context = LocalContext.current
//    val permission = Manifest.permission.ACCESS_FINE_LOCATION
//    val showRationalDialog by permissionViewModel.showRationaleDialog
//    val locationPermissionState by permissionViewModel.locationPermissionState
//    val permissionLauncher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
//            onResult = { isGranted ->
//                if (isGranted) {
//                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//                    permissionViewModel.updatePermissionState(true)
//                } else {
//                    if (PermissionUtils.shouldShowRationale(context, permission)) {
//                        permissionViewModel.updateShowRational(true)
//                    }
//                    Toast.makeText(context, "Location permission was denied.", Toast.LENGTH_SHORT)
//                        .show()
//                }
//            })
//
//    // Launch the permission request when the composable is first launched
//    LaunchedEffect(Unit) {
//        permissionLauncher.launch(permission)
//    }
//
//    if (showRationalDialog) {
//        RationalDialog(
//            onDismiss = { permissionViewModel.updateShowRational(false) },
//            onConfirm = {
//                permissionViewModel.updateShowRational(false)
//                permissionLauncher.launch(permission)
//            },
//        )
//    }
//
//    // If permission is granted, implement the feature
//    LaunchedEffect(locationPermissionState) {
//        if (locationPermissionState) {
//            val androidLocation = LocationHelper.getLastKnownLocation(context)
//            val location = androidLocation ?: Location(30.096, 31.34)
//            currentWeatherViewModel.loadWeather(location)
//        }
//    }
//}