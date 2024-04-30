import helper.input

/**
 *  협력을 어떻게 그려나갈지 먼저 이미지로 떠올리고, 납득가능한 흐름을 만들어보자.
 *
 *  1. 등장인물은 누가있을까?
 *   - 전체적인 프로그램의 흐름을 보여주는 Main
 *   - 정답
 *   - 게임을 기록하는 무언가..?
 *
 *  2. 각각의 등장인물들이 어떻게 협력할까?
 *   2-1.
 */

fun main() {
    val gameHistory = GameHistory()

    while (true) {
        println("#################### 야구게임 ####################")
        println("1. 게임 시작하기  2. 게임 기록 보기  3. 종료하기")
        when (input("입력 : ").toInt()) {
            1  -> {
                println("1. 1~9 Mode  2. 0~9 Mode")
                val game = when (input("입력 : ").toInt()) {
                    1 -> BaseballGame()
                    2 -> BaseballGameWithZero()
                    else -> continue
                }
                val tryCount = game.play()
                gameHistory.record(tryCount)
            }
            2 -> gameHistory.show()
            3 -> {
                println("\n< 숫자 야구 게임을 종료합니다 >")
                break
            }
            else -> println("올바른 숫자를 입력해주세요!")
        }
    }
}