package com.lajeet.workingexamples.architecture.mvvm.feature1.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.lajeet.workingexamples.architecture.mvvm.feature1.domain.repository.Feature1Repo
import javax.inject.Inject

class Feature1ViewModel @Inject constructor(
    private val feature1Repo: Feature1Repo
): ViewModel() {

    init {
        //do something
    }

    suspend fun doSomething() {
        feature1Repo.apiCalls()
    }
}