package com.lajeet.workingexamples.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.lajeet.practicecurrencyexchange.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BoundServiceExample: Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Action.START.toString() -> {
                start()
            }
            Action.STOP.toString() -> {
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        //do operation + show notification for foreground service
        someOperation()
        showNotification()
    }

    private fun someOperation() {
        scope.launch {
            while (true) {
                val value = (1..10000).random()
                Log.d(TAG, "someOperation: $value")
                delay(2000)
            }
        }
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Title")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(NOTIFICATION_ID, notification)
    }

    inner class LocalBinder: Binder() {
        fun getService(): BoundServiceExample = this@BoundServiceExample
    }

    companion object {
        const val TAG = "BoundServiceExample"
        const val CHANNEL_ID = "bounded_service_example"
        const val NOTIFICATION_ID = 2
    }
}