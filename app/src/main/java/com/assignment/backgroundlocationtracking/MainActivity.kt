package com.assignment.backgroundlocationtracking

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.assignment.backgroundlocationtracking.notification.CreateNotification
import com.assignment.backgroundlocationtracking.service.LocationService
import com.assignment.backgroundlocationtracking.ui.theme.BackgroundLocationTrackingTheme

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkAndRequestLocationPermission(this)
        setContent {
            BackgroundLocationTrackingTheme {
                Column(modifier = Modifier.fillMaxSize()) {

                    Button(onClick = {
                        Intent(applicationContext, LocationService::class.java).apply {
                            action = LocationService.ACTION_START
                            startService(this)
                        }
                    }) {
                        Text(text = "Start")
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        Intent(applicationContext, LocationService::class.java).apply {
                            action = LocationService.ACTION_STOP
                            // TODO  below also start service but with action stop as mentioned above
                            startService(this)
                        }
                    }) {
                        Text(text = "Stop")
                    }
                }
            }
        }

        /*
        checkAndRequestNotificationPermission(this)
        setContent {
            BackgroundLocationTrackingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateNotification("Creating Notification", this)
                }
            }
        }*/
    }
}


