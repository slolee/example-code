import helper.input

open class BaseballGame(val size: Int = 3) {

    protected open val answer = (1..9).shuffled().take(size)

    fun play(): Int {
        println("\n< 게임을 시작합니다 >")
        var tryCount = 0

        while (true) {
            // TODO : 사용자 입력을 별도로 함수를 통해서 받을지 말지는 선택의 문제다!
            val input = try {
                input("정답을 입력하세요 : ")
                    .toList()
                    .map { it.digitToInt() }
                    .also { if (!this.checkInput(it)) throw RuntimeException() }
            } catch (e: RuntimeException) {
                println("잘못된 입력입니다. 다시 입력해주세요!")
                continue
            }

            tryCount++
            val strike = this.countStrike(input)
            if (strike == size) {
                println("$strike 스트라이크! 정답입니다!")
                break
            }
            val ball = this.countBall(input)
            println("$strike 스트라이크! $ball 볼!")
        }

        println()
        return tryCount
    }

    protected open fun checkInput(input: List<Int>): Boolean {
        if (input.size != size) return false
        if (input.distinct().size != size) return false
        return true
    }

    private fun countStrike(input: List<Int>): Int {
        var count = 0
        for (i in 0..<size) {
            if (input[i] == answer[i]) {
                count++
            }
        }
        return count
    }

    private fun countBall(input: List<Int>): Int {
        var count = 0
        for (i in 0..<size) {
            if (input[i] != answer[i] && input[i] in answer) {
                count++
            }
        }
        return count
    }
}