package com.lajeet.workingexamples.architecture.mvvm.feature1.data.repository

import com.lajeet.workingexamples.architecture.mvvm.feature1.domain.repository.Feature1Repo

class Feature1RepoImpl: Feature1Repo {

    override suspend fun apiCalls() {
        //make the api call and return something
    }
}