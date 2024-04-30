import helper.input

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