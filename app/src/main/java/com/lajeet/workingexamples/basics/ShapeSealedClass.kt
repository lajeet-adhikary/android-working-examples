package com.lajeet.workingexamples.basics

/**
 * For each shape, different dimens are used as a param!
 * */
sealed class ShapeSealedClass {
    class Circle(val radius: Double) : ShapeSealedClass()
    class Oval(val width: Double, val height: Double) : ShapeSealedClass()
    class Rectangle(val width: Double, val height: Double) : ShapeSealedClass()
    class Square(val side: Double) : ShapeSealedClass()

    //this can also be done, which was not possible in ENUMS
    object objShape: ShapeSealedClass()
    sealed class newClass: ShapeSealedClass()
    sealed interface newInterface
}