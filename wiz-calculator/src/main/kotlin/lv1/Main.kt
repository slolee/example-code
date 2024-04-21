package lv1

import input

fun main() {
    val operator = input("Operator (+, -, *, /) : ")
    val x = input("First Number : ").toDouble()
    val y = input("Second Number : ").toDouble()

    val calculator = Calculator()
    if (operator == "+") {
        println("Result : ${calculator.add(x, y)}")
    } else if (operator == "-") {
        println("Result : ${calculator.subtract(x, y)}")
    } else if (operator == "*") {
        println("Result : ${calculator.multiply(x, y)}")
    } else if (operator == "/") {
        println("Result : ${calculator.divide(x, y)}")
    } else {
        throw RuntimeException("Wrong Operator!")
    }

//    when (operator) {
//        "+" -> println("Result : ${calculator.add(x, y)}")
//        "-" -> println("Result : ${calculator.subtract(x, y)}")
//        "*" -> println("Result : ${calculator.multiply(x, y)}")
//        "/" -> println("Result : ${calculator.divide(x, y)}")
//        else -> throw RuntimeException("Wrong Operator!")
//    }
}