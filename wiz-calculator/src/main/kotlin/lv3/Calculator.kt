package lv3

import lv3.operator.AddOperator
import lv3.operator.DivideOperator
import lv3.operator.MultiplyOperator
import lv3.operator.SubtractOperator

class Calculator {

    fun add(operator: AddOperator, x: Double, y: Double) = operator.operate(x, y)
    fun subtract(operator: SubtractOperator, x: Double, y: Double) = operator.operate(x, y)
    fun multiply(operator: MultiplyOperator, x: Double, y: Double) = operator.operate(x, y)
    fun divide(operator: DivideOperator, x: Double, y: Double) = operator.operate(x, y)
}