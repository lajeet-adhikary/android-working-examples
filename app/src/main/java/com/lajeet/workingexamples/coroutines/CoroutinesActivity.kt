package com.lajeet.workingexamples.coroutines

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lajeet.practicecurrencyexchange.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Concepts to look into:
 * 0. basics (coroutine scope & context)
 * 1. launch + async (coroutine builders)
 * 2. cancelling a coroutine (auto + manual)
 * 3. exception handling
 * 4. supervisor scope + job
 * 5. withContext + runBlocking
 * 6. delay + Thread.sleep
 * 7. race conditions handling
 * */
@AndroidEntryPoint
class CoroutinesActivity : AppCompatActivity() {

    private val viewModel: CoroutinesViewModel by viewModels()

    private lateinit var tvText: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_coroutines)
        initViews()
    }

    private fun initViews() {
        tvText = findViewById(R.id.tvText)
        btnStart = findViewById(R.id.btnStart)
        btnStart.setOnClickListener {
            onBtnStart()
        }
        btnStop = findViewById(R.id.btnStop)
        btnStop.setOnClickListener {
            onBtnStop()
        }
    }

    private fun onBtnStart() {
        viewModel.coroutineExampleWithWithContext()
    }

    private fun onBtnStop() {

    }
}