package com.lajeet.workingexamples.coroutines

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import javax.inject.Inject

@HiltViewModel
class CoroutinesViewModel @Inject constructor(

): ViewModel() {

    init {

    }

    fun normalCoroutine() {
        /**
         * Output:
         * outer start
         * start
         * outer end
         * end (after 2 sec)
         * */
        Log.d(TAG, "normalCoroutine: outer start ${Thread.currentThread().name}")
        viewModelScope.launch {
            Log.d(TAG, "normalCoroutine: start ${Thread.currentThread().name}")
            delay(2000)
            Log.d(TAG, "normalCoroutine: end ${Thread.currentThread().name}")
        }
        Log.d(TAG, "normalCoroutine: outer end ${Thread.currentThread().name}")
    }

    fun asyncCoroutine() {
        /**
         * Output (behaves same as normal coroutine, as we are not awaiting):
         * outer start
         * start
         * outer end
         * end (after 2 sec)
         * */
        Log.d(TAG, "asyncCoroutine: outer start")
        viewModelScope.async {
            Log.d(TAG, "asyncCoroutine: start")
            delay(2000)
            Log.d(TAG, "asyncCoroutine: end")
        }
        Log.d(TAG, "asyncCoroutine: outer end")
    }

    suspend fun asyncCoroutineWithAwait() {
        /**
         * Output:
         * outer start
         * start
         * end
         * outer end
         * */
        Log.d(TAG, "asyncCoroutine: outer start")
        val job = viewModelScope.async {
            Log.d(TAG, "asyncCoroutine: start")
            delay(2000)
            Log.d(TAG, "asyncCoroutine: end")
        }
        job.await()
        Log.d(TAG, "asyncCoroutine: outer end")
    }

    fun multipleLaunchCoroutines() {
        /**
         * Output:
         * outer start
         * launch 1 start
         * launch2 start
         * outer end
         * launch 2 end
         * launch 1 end
         * */
        Log.d(TAG, "multipleLaunchCoroutines: outer start")
        viewModelScope.launch {
            Log.d(TAG, "multipleCoroutines: launch 1 start")
            delay(3000)
            yield()
            Log.d(TAG, "multipleCoroutines: launch 1 end")
        }
        viewModelScope.launch {
            Log.d(TAG, "multipleCoroutines: launch 2 start")
            delay(1000)
            Log.d(TAG, "multipleCoroutines: launch 2 end")
        }
        Log.d(TAG, "multipleLaunchCoroutines: outer end")
    }

    suspend fun multipleAsyncCoroutines() {
        /**
         * Output (WRONG):
         * outer start
         * launch 1 start
         * launch 1 end
         * launch 2 start
         * outer end <------------------ NOTE THIS
         * launch 2 end
         *
         * Output (RIGHT):
         * outer start
         * launch 1 start
         * launch 1 end
         * outer end <------------------- NOTE THIS
         * launch 2 start
         * launch 2 end
         * */
        Log.d(TAG, "multipleAsyncCoroutines: outer start")
        val job1 = viewModelScope.async {
            Log.d(TAG, "multipleAsyncCoroutines: launch 1 start")
            delay(3000)
            Log.d(TAG, "multipleAsyncCoroutines: launch 1 end")
        }
        job1.await()
        val job2 = viewModelScope.async {
            Log.d(TAG, "multipleAsyncCoroutines: launch 2 start")
            delay(1000)
            Log.d(TAG, "multipleAsyncCoroutines: launch 2 end")
        }
        Log.d(TAG, "multipleAsyncCoroutines: outer end")
    }


    suspend fun multipleAsyncCoroutines2() {
        /**
         * Output:
         * outer start
         * launch 1 start
         * launch 2 start
         * launch 2 end
         * launch 1 end
         * outer end
         * */
        Log.d(TAG, "multipleAsyncCoroutines2: outer start ${Thread.currentThread().name}")
        val job1 = viewModelScope.async {
            Log.d(TAG, "multipleAsyncCoroutines2: launch 1 start ${Thread.currentThread().name}")
            delay(3000)
            Log.d(TAG, "multipleAsyncCoroutines2: launch 1 end ${Thread.currentThread().name}")
        }
        val job2 = viewModelScope.async {
            Log.d(TAG, "multipleAsyncCoroutines2: launch 2 start ${Thread.currentThread().name}")
            delay(1000)
            Log.d(TAG, "multipleAsyncCoroutines2: launch 2 end ${Thread.currentThread().name}")
        }
        job1.await()
        job2.await()
        Log.d(TAG, "multipleAsyncCoroutines2: outer end ${Thread.currentThread().name}")
    }

    fun normalCoroutineWithCoroutineScope() {
        /**
         * Output:
         * outer start : main thread
         * outer end : main thread
         * start : worker thread
         * end : worker thread
         * */
        Log.d(TAG, "normalCoroutine: outer start ${getThreadInfo()}")
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "normalCoroutine: start ${getThreadInfo()}")
            delay(2000)
            Log.d(TAG, "normalCoroutine: end ${getThreadInfo()}")
        }
        Log.d(TAG, "normalCoroutine: outer end ${getThreadInfo()}")
    }

    fun coroutineErrorExample() {
        /**
         * Output:
         * outer start : main
         * outer end : main
         * inner start : main
         * inner mid : main
         * inner end : main
         * ** APP CRASH due to exception **
         * */
        try {
            Log.d(TAG, "coroutineCancel: outer start ${getThreadInfo()}")
            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "coroutineCancel: inner start ${getThreadInfo()}")
                delay(2000)
                longRunningTaskWithException()
                Log.d(TAG, "coroutineCancel: inner mid ${getThreadInfo()}")
                delay(1000)
                Log.d(TAG, "coroutineCancel: inner end ${getThreadInfo()}")
            }
            Log.d(TAG, "coroutineCancel: outer end ${getThreadInfo()}")
        } catch (ex: Exception) {
            Log.d(TAG, "coroutineCancel: inside catch exception")
            ex.printStackTrace()
        }
    }

    fun coroutineErrorHandling() {
        /**
         * Output:
         * outer start : main
         * outer end : main
         * inner start : main
         * coroutine exception : But NO CRASH + No next operation is done in this coroutine
         * */
        try {
            Log.d(TAG, "coroutineCancel: outer start ${getThreadInfo()}")
            CoroutineScope(Dispatchers.Main + coroutineExceptionHandler).launch {
                Log.d(TAG, "coroutineCancel: inner start ${getThreadInfo()}")
                delay(2000)
                exception()
                Log.d(TAG, "coroutineCancel: inner mid ${getThreadInfo()}")
                delay(1000)
                Log.d(TAG, "coroutineCancel: inner end ${getThreadInfo()}")
            }
            Log.d(TAG, "coroutineCancel: outer end ${getThreadInfo()}")
        } catch (ex: Exception) {
            Log.d(TAG, "coroutineCancel: inside catch exception")
            ex.printStackTrace()
        }
    }

    fun coroutineExampleWithRunBlocking() {
        /**
         * Output:
         * outer start
         * outer end
         * inner start
         * inner run blocking start
         * inner run blocking end
         * inner end
         * */
        try {
            Log.d(TAG, "coroutineRunBlockingExample: outer start ${getThreadInfo()}")
            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "coroutineRunBlockingExample: inner start ${getThreadInfo()}")
                runBlocking {
                    Log.d(TAG, "coroutineRunBlockingExample: inner run blocking start ${getThreadInfo()}")
                    delay(2000)
                    Log.d(TAG, "coroutineRunBlockingExample: inner run blocking end ${getThreadInfo()}")
                }
                delay(1000)
                Log.d(TAG, "coroutineRunBlockingExample: inner end ${getThreadInfo()}")
            }
            Log.d(TAG, "coroutineRunBlockingExample: outer end ${getThreadInfo()}")
        } catch (ex: Exception) {
            Log.d(TAG, "coroutineRunBlockingExample: inside catch exception")
            ex.printStackTrace()
        }
    }

    fun coroutineExampleWithWithContext() {
        /**
         * Output:
         * outer start
         * outer end
         * inner start
         * inner withContext start <------- Run blocking kind of operation
         * inner withContext end
         * inner end
         * */
        try {
            Log.d(TAG, "coroutineExampleWithWithContext: outer start ${getThreadInfo()}")
            CoroutineScope(Dispatchers.Main).launch {
                Log.d(TAG, "coroutineExampleWithWithContext: inner start ${getThreadInfo()}")
                withContext(Dispatchers.IO) {
                    Log.d(TAG, "coroutineExampleWithWithContext: inner with context start ${getThreadInfo()}")
                    delay(2000)
                    Log.d(TAG, "coroutineExampleWithWithContext: inner with context end ${getThreadInfo()}")
                }
                Log.d(TAG, "coroutineExampleWithWithContext: inner end ${getThreadInfo()}")
            }
            Log.d(TAG, "coroutineExampleWithWithContext: outer end ${getThreadInfo()}")
        } catch (ex: Exception) {
            Log.d(TAG, "coroutineExampleWithWithContext: inside catch exception")
            ex.printStackTrace()
        }
    }

    fun coroutineExampleWithSupervisorScope() {
        /**
         * Output:
         *
         * */
        try {

        } catch (ex: Exception) {
            Log.d(TAG, "coroutineExampleWithSupervisorScope: inside catch exception")
            ex.printStackTrace()
        }
    }

    private fun getThreadInfo(): String {
        val thread = Thread.currentThread()
        return thread.name + " #" + thread.id
    }

    private fun longRunningTaskWithException() {
        //this will crash the app, NO MATTER WHAT
        //to stop this, we need to add the coroutine exception handler for the
        //coroutine where the exception might/will happen
        viewModelScope.launch {
            delay(2000)
            throw Exception("Something went wrong")
        }
    }

    private fun exception() {
        //this will crash the app, NO MATTER WHAT
        //to solve this, an exception handler is required
        throw Exception("Something went wrong")
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "Inside coroutine exception handler : ${throwable.printStackTrace()}")
    }

    companion object {
        const val TAG = "CoroutinesViewModel"
    }
}