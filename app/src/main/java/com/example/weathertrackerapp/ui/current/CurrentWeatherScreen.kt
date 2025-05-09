package com.example.weathertrackerapp.ui.current

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weathertrackerapp.R
import com.example.weathertrackerapp.ui.PermissionViewModel
import com.example.weathertrackerapp.ui.theme.WeatherTrackerAppTheme
import com.example.weathertrackerapp.utils.LocationHelper
import com.example.weathertrackerapp.utils.PermissionUtils

@Composable
fun CurrentWeatherScreen(
    permissionViewModel: PermissionViewModel, modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val permission = Manifest.permission.ACCESS_FINE_LOCATION
    val showRationalDialog by permissionViewModel.showRationaleDialog
    val locationPermissionState by permissionViewModel.locationPermissionState
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                if (isGranted) {
                    permissionViewModel.updatePermissionState(true)
                } else {
                    if (PermissionUtils.shouldShowRationale(context, permission)) {
                        permissionViewModel.updateShowRational(true)
                    }
                    Toast.makeText(context, "Location permission was denied.", Toast.LENGTH_SHORT)
                        .show()
                }

            })

    // Launch the permission request when the composable is first launched
    LaunchedEffect(Unit) {
        permissionLauncher.launch(permission)
    }

    if (showRationalDialog) {
        RationalDialog(
            onDismiss = { permissionViewModel.updateShowRational(false) },
            onConfirm = {
                permissionViewModel.updateShowRational(false)
                permissionLauncher.launch(permission)
            },
        )
    }

    // If permission is granted, implement the feature (e.g., camera functionality)
    if(locationPermissionState){
        Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()

    }
}

@Composable
fun DeleteMe(loc:String) {
    Card (modifier = Modifier) {
        Row {
            Image(imageVector = Icons.Filled.Star, contentDescription = null)
            Text(text = "34 C")
        }
    }
}
@Composable
fun RationalDialog(
    title: String = stringResource(R.string.location_permission_title),
    body: String = stringResource(R.string.location_permission_body),
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(onDismissRequest = onDismiss, title = {
        Text(text = title)
    }, text = {
        Text(text = body)
    }, confirmButton = {
        Button(onClick = onConfirm) {
            Text(stringResource(R.string.confirm))
        }
    }, dismissButton = {
        TextButton(onClick = onConfirm) {
            Text(stringResource(R.string.cancel))
        }
    })
}

@Preview(showBackground = true)
@Composable
fun CurrentWeatherScreenPreview() {
    WeatherTrackerAppTheme {
//        CurrentWeatherScreen()
    }
}