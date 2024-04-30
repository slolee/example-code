class GameHistory {

    private val history = mutableListOf<Int>()

    fun record(score: Int) {
        history.add(score)
    }

    fun show() {
        println("\n< 게임 기록 보기 >")
        history.forEachIndexed { index, score ->
            println("${index + 1}번째 게임 : 시도 횟수 - $score")
        }
        println()
    }
}
