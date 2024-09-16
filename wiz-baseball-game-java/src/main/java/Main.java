import static helper.InputHelper.input;

/**
 * 1. 가장 먼저 해야할 것은 내 문제를 해결하기 위해 어떤 협력이 필요하고, 그 협력에 참여하기 위한 어떤 등장인물들이 필요한지 결정하는 일이다.
 * 2. 어떤 등장인물들이 존재하면 좋을까?
 * 3. 위 등장인물들이 어떻게 협력하면 되는지 이미지를 먼저 그려보자!
 * <p>
 * 중요한건 아직 코드 작성을 시작하지 않았다는 것이다!!
 * 1. 내가 구현할 전체적인 흐름을 완전히 장악해 이미지화할 수 있어야한다.
 * 2. 이미지화한 흐름을 구현하기 위해 어떤 등장인물들이 필요한지 떠올린다.
 * 3. 각각의 등장인물들이 어떤 대화를 거치며 문제를 해결하는지 설명한다.
 * <p>
 * 그리고나서 객체 하나하나의 흐름으로 들어간다. 각각의 객체에 대해서 어떻게 흘러가면 될지, 납득가능한 한글 설명을 작성해보자.
 * <p>
 * [ Main ]
 * 1) 무한루프를 돌며 사용자 입력을 받는다. (1. 게임시작, 2. 게임기록 보기, 3. 종료하기 ...)
 * 2) 1번 입력시
 * 2-1) 게임 객체를 생성한다.
 * 2-2) 게임 객체에게 시작(play) 요청을 보내 게임을 시작한다.
 * 2-3) 게임 객체를 GameHistory 의 record 요청을 통해 넘겨, 게임 이력을 기록한다.
 * 3) 2번 입력시
 * 3-1) 게임 기록객체(GameHistory)에게 게임기록 조회를 위한 show 요청을 보낸다.
 * 4) 3번 입력시
 * 4-1) 무한루프를 탈출해 프로그램을 종료한다.
 * <p>
 * [ BaseballGame.play() ]
 * 1) 사용자로부터 정답을 입력받는다.
 * 2) 사용자의 입력이 올바른지 검증한다.  -> 추상화 수준에 대한 설명 필수!!!
 * 3) tryCount 를 1 증가시킨다.
 * 4) 스트라이크 개수를 센다.
 * 5) 스트라이크 개수가 정답의 길이와 같다면, 정답으로 처리하고 게임을 종료한다.
 * 6) 볼 개수를 센다.
 * 7) 힌트를 노출하고 게임을 계속한다.
 * <p>
 * [ GameHistory.record(game) ]
 * -> 관심사의 분리! 관심끄자!
 * -> 여기서 중요한건, 아직 "어떻게, 어디에 저장할지 결정하지 않았다는 것" 이다!!! (캡슐화 설명 포함)
 * -> 그 말은, GameHistory 가 인스턴스 변수에 저장하든지, 데이터베이스에 저장하든지, 파일에 저장하든지 상관없다.
 * -> 다른말로 이러한 변화가 생겨도 Client 객체에 영향을 주지 않는다는 말이다.
 */
public class Main {
	public static void main(String[] args) {
		GameHistory history = new GameHistory();

		while (true) {
			System.out.println("#################### 야구게임 ####################");
			System.out.println("1. 게임 시작하기  2. 게임 기록 보기  3. 종료하기");
			try {
				switch (Integer.parseInt(input("입력 : "))) {
					case 1 -> {
						System.out.println("1. Lv1(3자리, 1~9)  2. Lv2(3자리, 0~9)");
						var game = switch (Integer.parseInt(input("게임선택 : "))) {
							case 1 -> new BaseballGame();
							case 2 -> new BaseballGameWithZero();
							default -> throw new IllegalStateException("올바른 숫자를 입력해주세요!");
						};
						game.play();
						history.record(game);
					}
					case 2 -> history.show();
					case 3 -> {
						System.out.println("\n< 숫자 야구 게임을 종료합니다 >");
						return;
					}
					default -> throw new IllegalStateException("올바른 숫자를 입력해주세요!");
				}
			} catch (IllegalStateException ex) {
				System.out.println(ex.getMessage() + " 처음으로 돌아갑니다.\n");
			}
		}
	}
}
