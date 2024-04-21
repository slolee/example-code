package lv3.operator

class DivideOperator {

    fun operate(x: Double, y: Double): Double {
        if (y == 0.0) {
            throw ArithmeticException("Cannot be divided by zero!")
        }
        return x / y
    }
}