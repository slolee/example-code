import static helper.InputHelper.input;

import java.util.*;
import java.util.stream.Collectors;

public class BaseballGame {
	private final List<Character> availableChars;
	private final int size;
	private final String answer;

	private int tryCount = 0;

	public BaseballGame() {
		this(3, List.of('1', '2', '3', '4', '5', '6', '7', '8', '9'));
	}

	public BaseballGame(int size) {
		this(size, List.of('1', '2', '3', '4', '5', '6', '7', '8', '9'));
	}

	public BaseballGame(List<Character> availableChars) {
		this(3, availableChars);
	}

	public BaseballGame(int size, List<Character> availableChars) {
		this.size = size;
		this.availableChars = availableChars;
		this.answer = this.generateAnswer();
	}

	/**
	 * [ BaseballGame.play() ]
	 * 1) 사용자로부터 정답을 입력받는다.
	 * 2) 사용자의 입력이 올바른지 검증한다.  -> 추상화 수준에 대한 설명 필수!!!
	 * 3) tryCount 를 1 증가시킨다.
	 * 4) 스트라이크 개수를 센다.
	 * 5) 스트라이크 개수가 정답의 길이와 같다면, 정답으로 처리하고 게임을 종료한다.
	 * 6) 볼 개수를 센다.
	 * 7) 힌트를 노출하고 게임을 계속한다.
	 */
	public void play() {
		System.out.println("\n< 게임을 시작합니다 >");

		while (true) {
			String input = input("정답을 입력하세요 : ");
			if (!this.validateInput(input)) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요!");
				continue;
			}

			this.tryCount++;
			int strike = this.countStrike(input);
			if (strike == this.size) {
				System.out.println(strike + " 스트라이크! 정답입니다!");
				break;
			}
			int ball = this.countBall(input);
			System.out.println(strike + " 스트라이크, " + ball + " 볼!");
		}
	}

	public String getResult() {
		return "시도 횟수 - " + this.tryCount;
	}

	private boolean validateInput(String input) {
		Set<Character> seen = new HashSet<>();
		for (char c : input.toCharArray()) {
			if (!seen.add(c))
				return false;
			if (!availableChars.contains(c))
				return false;
		}
		return seen.size() == this.size;
	}

	/**
	 * generateAnswer() 와 같은건 작은 문제!!
	 * 전체적인 이미지와 흐름을 결정하는게 먼저고, 그 과정에서 분할된 작은 문제들이 나온다.
	 * "관심사의 분리" 를 통해서 작은 문제들을 더욱 쉽게 해결할 수 있고, 이 전체적인 과정이 일종의 CS 관점에서 분할정복이라고 할 수 있다.
	 * 우리는 작은 문제에만 집중해서 더욱 문제를 쉽고 빠르게 해결할 수 있다.
	 */
	private String generateAnswer() {
		List<Character> targets = new ArrayList<>(availableChars);
		Collections.shuffle(targets);
		return targets.stream()
			.limit(this.size)
			.map(String::valueOf)
			.collect(Collectors.joining());
	}

	private int countStrike(String input) {
		var count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == this.answer.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	private int countBall(String input) {
		var count = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != this.answer.charAt(i)
				&& this.answer.contains(String.valueOf(input.charAt(i)))) {
				count++;
			}
		}
		return count;
	}
}
