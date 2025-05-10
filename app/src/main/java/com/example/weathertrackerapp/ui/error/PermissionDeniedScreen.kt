package com.example.weathertrackerapp.ui.error

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weathertrackerapp.ui.PermissionViewModel


@Composable
fun PermissionDeniedScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Permission is required to continue")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("main_entry_screen") {
                popUpTo("permission_denied_screen") { inclusive = true }
            }
        }) {
            Text("Try Again")
        }
    }
}
