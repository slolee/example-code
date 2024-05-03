import helper.input

open class BaseballGame(private val size: Int = 3) {

    protected open val answer = (1..9).shuffled().take(size)

    /**
     *   BaseballGame.play()
     *    1. tryCount 을 0 으로 초기화한다.
     *    2. 사용자에게 정답 입력을 받는다.
     *    3. 사용자에게 입력받은 입력이 올바른지 검증한다.
     *    4. tryCount 을 1 증가시킨다.
     *    5. 스트라이크가 몇개인지 계산한다.
     *    6. 만약 스트라이크가 size 와 동일하다면 정답임으로 게임을 종료한다. (이때 TryCount 반환한다)
     *    7. 볼이 몇개인지 계산한다.
     *    8. 힌트를 출력하고 처음으로 돌아간다.
     *
     *   // 적당한 추상화가 적용된 설명 -> 읽기 쉬운 코드 만들어짐
     *   // 캡슐화를 구현할 수 있다 -> 구체적인걸 늦게 결정하기 때문
     *   // 관심사 분리를 통해서 문제를 작게 만들고, 생각하는 범위자체를 작게 만들수 있다.
     */
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