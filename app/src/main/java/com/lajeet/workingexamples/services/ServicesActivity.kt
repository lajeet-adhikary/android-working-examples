package com.lajeet.workingexamples.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.lajeet.practicecurrencyexchange.R

class ServicesActivity : AppCompatActivity() {

    private lateinit var startNormalServiceButton: Button
    private lateinit var stopNormalServiceButton: Button

    private lateinit var startBoundServiceButton: Button
    private lateinit var stopBoundServiceButton: Button

    private var serviceConnected = false
    var boundService: BoundServiceExample? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_services)
        initViews()
    }

    private fun initViews() {
        startNormalServiceButton = findViewById(R.id.btnStartService)
        stopNormalServiceButton = findViewById(R.id.btnStopService)
        startBoundServiceButton = findViewById(R.id.btnStartBoundService)
        stopBoundServiceButton = findViewById(R.id.btnStopBoundService)

        startNormalServiceButton.setOnClickListener {
            startNormalService()
        }
        stopNormalServiceButton.setOnClickListener {
            stopNormalService()
        }
        startBoundServiceButton.setOnClickListener {
            startBoundService()
        }
        stopBoundServiceButton.setOnClickListener {
            stopBoundService()
        }
    }

    private fun startNormalService() {
        Intent(this, NormalServiceExample::class.java).also {
            it.action = Action.START.toString()
            startService(it)
        }
    }

    private fun stopNormalService() {
        Intent(this, NormalServiceExample::class.java).also {
            it.action = Action.STOP.toString()
            startService(it)
        }
    }

    private fun startBoundService() {
        Intent(this, BoundServiceExample::class.java).also {
            it.action = Action.START.toString()
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
            startService(it)
        }
    }

    private fun stopBoundService() {
        if (!serviceConnected) return
        //calling a function inside the service, but just this will not ensure that the service will stop
        boundService?.stopService()
        unbindService(serviceConnection) //disconnecting the ServiceConnection obj
        //updating variables
        serviceConnected = false
        boundService = null
    }

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            //able to access the service through the IBinder object, which returns the service instance
            boundService = (service as BoundServiceExample.LocalBinder).getService()
            serviceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceConnected = false
            boundService = null
        }
    }
}