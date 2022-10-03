package com.jaehyeon.backgroundlocaitonservice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

/**
 * Created by Jaehyeon on 2022/10/03.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val channel = NotificationChannel(
            "location",
            "Location",
            NotificationManager.IMPORTANCE_LOW
        )

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}