package lv4.operator

class DivideOperator : AbstractOperator {

    override fun operate(x: Double, y: Double): Double {
        if (y == 0.0) {
            throw ArithmeticException("Cannot be divided by zero!")
        }
        return x / y
    }
}