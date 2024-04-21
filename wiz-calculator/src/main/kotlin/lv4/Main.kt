package lv4

import input
import lv4.operator.AddOperator
import lv4.operator.DivideOperator
import lv4.operator.MultiplyOperator
import lv4.operator.SubtractOperator

fun main() {
    val operator = input("Operator (+, -, *, /) : ")
    val x = input("First Number : ").toDouble()
    val y = input("Second Number : ").toDouble()

    val calculator = Calculator()
    when (operator) {
        "+" -> AddOperator()
        "-" -> SubtractOperator()
        "*" -> MultiplyOperator()
        "/" -> DivideOperator()
        else -> throw RuntimeException("Wrong Operator!")
    }.let {
        println("Result : ${calculator.operate(it, x, y)}")
    }
}

/**
 *  Lv3 와 비교해서 어떤 점이 나아졌을까?
 *   # 1.
 *   - Calculator 객체가 AddOperator, SubtractOperator, MultiplyOperator, DivideOperator 를 직접 몰라도 된다.
 *   - 이는 또 다른 연산자를 처리하는 객체가 등장하더라도 Calculator 객체가 같이 수정되지 않아도된다는 의미를 가진다.
 *   - 즉, 변경이 발생했을 때 수정되어야하는 범위가 적고, 이는 변경에 유연한 구조가 된다.
 *
 *   # 2.
 *   - Main 에서 각각의 연산자 객체를 AbstractOperator 타입의 객체로 Upcasting 할 수 있다.
 *   - 결과를 출력하는 과정을 추상화된 AbstractOperator 타입의 객체로 공통처리할 수 있다는 장점도 있다.
 */
