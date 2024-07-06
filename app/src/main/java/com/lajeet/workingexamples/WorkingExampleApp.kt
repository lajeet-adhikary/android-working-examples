package com.lajeet.workingexamples

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.lajeet.workingexamples.services.BoundServiceExample
import com.lajeet.workingexamples.services.NormalServiceExample
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WorkingExampleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        registerNotifications()
    }

    private fun registerNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationChannels().forEach {
                notificationManager.createNotificationChannel(it)
            }
        }
    }

    private fun notificationChannels(): List<NotificationChannel> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannelForNormalService = NotificationChannel(
                NormalServiceExample.CHANNEL_ID,
                "Normal Service", NotificationManager.IMPORTANCE_HIGH)
            val notificationChannelForBoundService = NotificationChannel(
                BoundServiceExample.CHANNEL_ID,
                "Bound Service", NotificationManager.IMPORTANCE_HIGH)
            listOf(
                notificationChannelForNormalService, notificationChannelForBoundService
            )
        } else emptyList()
    }
}