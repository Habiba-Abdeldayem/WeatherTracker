package com.example.weathertrackerapp.data.model

// Used in UI to determine API status
sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val message: String): ApiResponse<Nothing>()
    object Loading: ApiResponse<Nothing>()
}