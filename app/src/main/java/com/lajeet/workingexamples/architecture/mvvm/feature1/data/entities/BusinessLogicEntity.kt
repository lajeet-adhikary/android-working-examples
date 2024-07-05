package com.lajeet.workingexamples.architecture.mvvm.feature1.data.entities

/**
 * Business Level Entity objects (best practice is to convert them into custom made Models and use them App wide)
 * Ideally this entity should only be used in the data package layer
 * */
data class BusinessLogicEntity(
    val id: Int,
    //etc.
)
