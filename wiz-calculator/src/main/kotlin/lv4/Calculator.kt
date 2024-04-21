package lv4

import lv4.operator.AbstractOperator

class Calculator {

    fun operate(operator: AbstractOperator, x: Double, y: Double) = operator.operate(x, y)
}