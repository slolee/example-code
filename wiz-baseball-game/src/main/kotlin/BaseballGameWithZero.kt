import kotlin.random.Random

class BaseballGameWithZero : BaseballGame() {

    override val answer: List<Int>

    init {
        val first = (1..9).random()
        answer = listOf(first) +
                (0..9).filterNot { it == first }
                    .shuffled()
                    .take(size - 1)
    }

    override fun checkInput(input: List<Int>): Boolean {
        if (input[0] == 0) return false
        return super.checkInput(input)
    }
}