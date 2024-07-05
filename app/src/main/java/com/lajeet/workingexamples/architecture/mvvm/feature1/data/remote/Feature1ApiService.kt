package com.lajeet.workingexamples.architecture.mvvm.feature1.data.remote

import retrofit2.http.GET

interface Feature1ApiService {

    @GET("url/")
    suspend fun apiCall()
}