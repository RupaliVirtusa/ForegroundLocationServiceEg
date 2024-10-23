package com.assignment.backgroundlocationtracking.notification

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.assignment.backgroundlocationtracking.R


@Composable
fun CreateNotification(name: String, applicationContext: Context) {
    Column(
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = name,
            textAlign = TextAlign.Center
        )
        Button(onClick = {
            showAppNotification(applicationContext)
        }) {
            Text(text = applicationContext.getString(R.string.channel_name))
        }
    }

}

fun showAppNotification(applicationContext: Context) {
    NotificationHelper.createNotificationChannel(applicationContext)
    NotificationHelper.buildNotification(applicationContext)
    NotificationHelper.showNotification()
}
