package com.assignment.backgroundlocationtracking.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.assignment.backgroundlocationtracking.MainActivity
import com.assignment.backgroundlocationtracking.R
import kotlin.random.Random

internal object NotificationHelper {
    private const val NOTIFICATION_CHANNEL_ID = "my_notification_channel"
    lateinit var notificationManager: NotificationManager
    lateinit var notification: Notification

    fun createNotificationChannel(context: Context) {
        notificationManager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.channel_name), NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun buildNotification(context: Context): Notification {
        notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Content Title")
            .setContentText("Content Text")
            .setSmallIcon(androidx.core.R.drawable.notification_bg)
            .setContentIntent(Intent(context, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            })
            .build()
        return notification
    }

    fun showNotification() {
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}