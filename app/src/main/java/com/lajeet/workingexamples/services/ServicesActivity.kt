package com.lajeet.workingexamples.services

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lajeet.practicecurrencyexchange.R

class ServicesActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var stopButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_services)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        initViews()
    }

    private fun initViews() {
        startButton = findViewById(R.id.btnStartService)
        stopButton = findViewById(R.id.btnStopService)

        startButton.setOnClickListener {
            startService()
        }
        stopButton.setOnClickListener {
            stopService()
        }
    }

    private fun startService() {
        Intent(this, NormalServiceExample::class.java).also {
            it.action = Action.START.toString()
            startService(it)
        }
    }

    private fun stopService() {
        Intent(this, NormalServiceExample::class.java).also {
            it.action = Action.STOP.toString()
            startService(it)
        }
    }
}