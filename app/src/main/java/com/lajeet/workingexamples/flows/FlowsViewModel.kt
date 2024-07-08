package com.lajeet.workingexamples.flows

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

/**
 * Flows:
 * 1. Cold: Emit only when there is a consumer
 * 2. Hot: Emit, even if there is no consumer
 *      2.a. StateFlow: holds data and emit updates
 *      2.b. SharedFlow: can be collected by multiple collectors
 * */
class FlowsViewModel: ViewModel() {

    init {

    }

    fun startNormalFlow() {
        //Cold flow: will emit values only when there is a consumer
        Log.d(TAG, "startNormalFlow: init")
        flow<Int> {
            while (true) {
                val value = (1..10000).random()
                emit(value)
                Log.d(TAG, "startNormalFlow emitting: $value")
                delay(2000)
            }
        }
    }

    fun getColdFlow() = flow<Int> {
        while (true) {
            val value = (1..10000).random()
            emit(value)
            Log.d(TAG, "getColdFlow emitting: $value")
            delay(2000)
        }
    }

    companion object {
        const val TAG = "FlowsViewModel"
    }
}