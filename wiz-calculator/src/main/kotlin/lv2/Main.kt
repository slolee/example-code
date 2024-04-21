package lv2

import input

fun main() {
    val operator = input("Operator (+, -, *, /, %) : ")
    val x = input("First Number : ").toDouble()
    val y = input("Second Number : ").toDouble()

    val calculator = Calculator()
    when (operator) {
        "+" -> println("Result : ${calculator.add(x, y)}")
        "-" -> println("Result : ${calculator.subtract(x, y)}")
        "*" -> println("Result : ${calculator.multiply(x, y)}")
        "/" -> println("Result : ${calculator.divide(x, y)}")
        "%" -> println("Result : ${calculator.remain(x, y)}")
        else -> throw RuntimeException("Wrong Operator!")
    }
}