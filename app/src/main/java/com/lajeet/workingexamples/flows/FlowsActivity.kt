package com.lajeet.workingexamples.flows

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.lajeet.practicecurrencyexchange.R
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlowsActivity : AppCompatActivity() {

    private val viewModel: FlowsViewModel by viewModels()

    private lateinit var tvText: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.layout_flows)
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

        observe()
    }

    private fun onBtnStart() {
        lifecycleScope.launch {
            Log.d(FlowsViewModel.TAG, "onBtnStart: ")
            val job = async { viewModel.getColdFlow().collect() }

            delay(2000)
            Log.d(FlowsViewModel.TAG, "onBtnStart: AGAIN")
            viewModel.getColdFlow().collect()
            job.await()
        }
    }

    private fun onBtnStop() {

    }

    private fun observe() {

    }
}