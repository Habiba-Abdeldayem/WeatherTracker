package com.example.weathertrackerapp.ui


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {

    private val _locationPermissionState = mutableStateOf(false)
    val locationPermissionState: State<Boolean> get() = _locationPermissionState

    private val _showRationaleDialog = mutableStateOf(false)
    val showRationaleDialog: State<Boolean> get() = _showRationaleDialog

    fun updatePermissionState(newValue: Boolean = false) {
        _locationPermissionState.value = newValue
    }

    fun updateShowRational(newValue: Boolean = true) {
        _showRationaleDialog.value = newValue
    }

}