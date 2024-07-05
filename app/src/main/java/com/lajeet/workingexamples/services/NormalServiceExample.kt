package com.lajeet.workingexamples.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.lajeet.practicecurrencyexchange.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NormalServiceExample: Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
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
        //this coroutine will run endlessly, not attached to the service lifecycle
        //even if the service is destroyed, it will run
        //to cancel this coroutine, you will have to use scope.cancel() explicitly in the onDestroy() lifecycle method
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

    override fun onDestroy() {
        scope.cancel("service is destroying, so cancel this")
        Log.d(TAG, "NormalServiceExample : onDestroy")
        super.onDestroy()
    }

    companion object {
        const val TAG = "NormalServiceExample"
        const val CHANNEL_ID = "normal_service_example"
        const val NOTIFICATION_ID = 1
    }
}