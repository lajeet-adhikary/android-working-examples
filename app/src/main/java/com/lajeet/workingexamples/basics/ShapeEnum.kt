package com.lajeet.workingexamples.basics

/**
 * Limitation is that it can only have constants
 * Consider a scenario, where you need to apply formula for all the shape for a varying dimension.
 * If using enums, that will not be possible unless we include all the dimens as params for all the enums.
 *
 * In this scenario, a sealed class can be used.
 * */
enum class ShapeEnum {
    CIRCLE, OVAL, RECTANGLE, SQUARE
}