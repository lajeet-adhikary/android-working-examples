package com.lajeet.workingexamples.threading

import android.os.Handler
import android.os.Looper

class ThreadingSamples {

    private var testThread: Thread? = null

    private var testHandler: Handler? = null

    init {
        initThread()
        startThread()

        initHandler()
        startHandler()
    }

    private fun initThread() {
        testThread = Thread {
            for (i in 1..5) {
                println("Task in thread: $i")
                Thread.sleep(500) // Sleep for 500ms to simulate a time-consuming task
            }
        }.apply {
            name = "testing a thread"
            priority = Thread.MAX_PRIORITY
        }
    }

    private fun startThread() {
        println("Thread started.")
        testThread?.start()

        // Main thread task
        for (i in 1..5) {
            println("Task in main thread: $i")
            Thread.sleep(300) // Sleep for 300ms to simulate a time-consuming task
        }

        // Wait for the thread to finish
        testThread?.join()

        println("Thread completed.")
    }

    private fun initHandler() {
        testHandler = Handler(Looper.getMainLooper())
    }

    private fun startHandler() {
        testHandler?.postDelayed(
            {
                println("Handler task")
            }, 1000
        )

        // Main thread task
        for (i in 1..3) {
            println("Task in main thread: $i")
            Thread.sleep(300) // Sleep for 300ms to simulate a task
        }

        // Keep the main thread alive for a while to see the handler's task complete
        Thread.sleep(2000)
    }
}