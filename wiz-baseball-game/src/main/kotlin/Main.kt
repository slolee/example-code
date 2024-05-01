import helper.input

/**
 *   어떤 등장인물들이 등장하면 좋을까?
 *    - 전체적인 흐름을 통제하기 위한 Main
 *    - 각각의 게임에 대한 객체 - BaseballGame & BaseballGameWithZero
 *    - 게임의 이력을 관리하기 위한 GameHistory
 *
 *   위 등장인물들이 어떻게 협력하면 되는지 이미지를 그려보자!
 *    - Main -> BaseballGame  : 게임을 시작하기 위해 play 요청을 보내고, 게임의 결과를 응답으로 받는다.
 *          (게임이 어떻게 흘러가는지 Main 은 관심없다!)
 *    - Main -> GameHistory  : BaseballGame 으로부터 받은 게임의 결과를 기록하기 위해 record 요청을 보낸다.
 *    - Main -> GameHistory  : 게임 기록을 보기위해서 기록되어있는 기록을 출력해달라는 show 요청을 보낸다.
 *
 *   각각의 객체가 어떻게 흘러가면 될까? 일단 납득가능한 한글로 먼저 써보자!!
 *    - Main
 *      1) 무한루프를 돌며 사용자 입력을 받는다. (1. 게임 시작, 2. 게임 기록 보기, 3. 종료하기, 이외. 재시도)
 *      2) 1번 입력시 :
 *          2-1) 게임 객체를 생성한다.
 *          2-2) 게임 객체(BaseballGame)에게 게임 시작(play)을 요청한다.
 *          2-3) 게임 객체에게 받은 결과를 게임 기록객체(GameHistory)에게 기록(record)을 요청한다.
 *      3) 2번 입력시 :
 *          3-1) 게임 기록객체(GameHistory)에게 조회(show)를 요청한다.
 *      4) 3번 입력시 :
 *          4-1) 무한루프를 탈출해 프로그램을 종료한다.
 *
 *    - BaseballGame.play()
 *      1) tryCount 를 0으로 초기화한다.
 *      2) 사용자에게 숫자 입력을 받는다.
 *      3) 올바른 숫자가 입력되었는지 검증한다.
 *      4) tryCount 를 1 증가시킨다.
 *      5) 스트라이크가 몇개인지 확인한다.
 *         만약 스트라이크가 정답크기만큼 나왔다면 정답임으로 게임을 종료한다.
 *      6) 볼이 몇개인지 확인한다.
 *      7) 스트라이크, 볼의 개수를 출력해 힌트를 제공한다.
 *
 *      Hint. 이 시점에 "스트라이크가 몇개인지 어떻게 확인하지..? 볼이 몇개인지 어떻게 확인하지..?" 이런 고민하지말자!
 *            이게 관심사의 분리이고, 캡슐화를 지킬 수 있는 가이드가 된다.
 *            상위 흐름을 단순화해서 코드를 작성하고 읽기 쉽게 만들어주고, 구체적인 함수의 자유도를 높여 변경에 유연해진다.
 *
 *    - GameHistory.record(tryCount)
 *      1) 전달받은 기록을 (인스턴스 변수에) 저장한다.
 *
 *    - GameHistory.show()
 *      1) 인스턴스 변수에 저장해뒀던 기록들을 순환하며 출력한다.
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