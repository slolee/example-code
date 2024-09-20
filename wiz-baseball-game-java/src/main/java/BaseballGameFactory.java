import static helper.InputHelper.*;

public class BaseballGameFactory {
	public static BaseballGame generate() {
		return generateWithSize(3);
	}

	public static BaseballGame generateWithSize(int size) {
		if (size < 3 || size > 5) {
			throw new IllegalStateException("3~5 자리 게임만 가능합니다.");
		}
		System.out.println("\n1. Lv1(" + size + "자리, 1~9)  2. Lv2(" + size + "자리, 0~9)");
		return switch (Integer.parseInt(input("게임선택 : "))) {
			case 1 -> new BaseballGame(size);
			case 2 -> new BaseballGameWithZero(size);
			default -> throw new IllegalStateException("올바른 숫자를 입력해주세요!");
		};
	}
}
