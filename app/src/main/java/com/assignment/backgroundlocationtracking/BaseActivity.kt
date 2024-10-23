package com.assignment.backgroundlocationtracking

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.FOREGROUND_SERVICE_LOCATION
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

open class BaseActivity : ComponentActivity() {
    // we need notification permission to be able to display a notification for the foreground service
    val notificationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            // if permission was denied, the service can still run only the notification won't be visible
        }

    // we need location permission to be able to start the service
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted, service can run
            }

            permissions.getOrDefault(FOREGROUND_SERVICE_LOCATION, false) -> {
                // Precise location access granted, service can run
            }

            permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted, service can still run.
            }

            else -> {
                // No location access granted, service can't be started as it will crash
                Toast.makeText(this, "Location permission is required!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkAndRequestLocationPermission(context: Context) {
        locationPermissionRequest.launch(
            arrayOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                FOREGROUND_SERVICE_LOCATION
            )
        )
    }

    fun checkAndRequestNotificationPermission(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            )) {
                android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                    // permission already granted
                }

                else -> {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }


}