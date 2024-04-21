package lv1

class Calculator {

    fun add(x: Double, y: Double) = x + y
    fun subtract(x: Double, y: Double) = x - y
    fun multiply(x: Double, y: Double) = x * y
    fun divide(x: Double, y: Double): Double {
        if (y == 0.0) {
            throw ArithmeticException("Cannot be divided by zero!")
        }
        return x / y
    }
}