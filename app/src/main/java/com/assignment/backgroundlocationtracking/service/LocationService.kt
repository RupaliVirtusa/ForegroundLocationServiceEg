package com.assignment.backgroundlocationtracking.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.assignment.backgroundlocationtracking.R
import com.assignment.backgroundlocationtracking.location.DefaultLocationClient
import com.assignment.backgroundlocationtracking.location.LocationClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.v("Service =>", "onCreate")
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.v("Service =>", "onStartCommand")
        when (intent?.action) {
            ACTION_START -> {
                start()
            }

            ACTION_STOP -> {
                stop()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }


    private fun start() {
        val notification = NotificationCompat.Builder(
            this,
            applicationContext.getString(R.string.location_channel_id)
        )
            .setContentTitle("Tracking Location")
            .setContentText("Location : Null")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setOngoing(true)

        val notificationManager =
            applicationContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        locationClient.getLocationUpdates(10000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val lat = location.latitude.toString()
                val long = location.longitude.toString()
                val updatedNotification = notification.setContentText("Location : ($lat, $long)")
                notificationManager.notify(1, updatedNotification.build())
            }

        startForeground(1, notification.build())

    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}