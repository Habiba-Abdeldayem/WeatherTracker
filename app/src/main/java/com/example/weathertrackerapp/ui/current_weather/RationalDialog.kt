package com.example.weathertrackerapp.ui.current_weather

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.weathertrackerapp.R

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