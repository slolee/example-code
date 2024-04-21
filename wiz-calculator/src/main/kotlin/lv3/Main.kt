package lv3

import input
import lv3.Calculator
import lv3.operator.AddOperator
import lv3.operator.DivideOperator
import lv3.operator.MultiplyOperator
import lv3.operator.SubtractOperator

fun main() {
    val operator = input("Operator (+, -, *, /) : ")
    val x = input("First Number : ").toDouble()
    val y = input("Second Number : ").toDouble()

    val calculator = Calculator()
    when (operator) {
        "+" -> println("Result : ${calculator.add(AddOperator(),  x, y)}")
        "-" -> println("Result : ${calculator.subtract(SubtractOperator(),  x, y)}")
        "*" -> println("Result : ${calculator.multiply(MultiplyOperator(), x, y)}")
        "/" -> println("Result : ${calculator.divide(DivideOperator(),  x, y)}")
        else -> throw RuntimeException("Wrong Operator!")
    }
}