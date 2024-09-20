import static helper.InputHelper.*;

public class BaseballGameFactory {
	public static BaseballGame generateGame() {
		return generateGame(3);
	}

	public static BaseballGame generateGame(int size) {
		System.out.println("\n1. Lv1(" + size + "자리, 1~9)  2. Lv2(" + size + "자리, 0~9)");
		return switch (Integer.parseInt(input("게임선택 : "))) {
			case 1 -> new BaseballGame(size);
			case 2 -> new BaseballGameWithZero(size);
			default -> throw new IllegalStateException("올바른 숫자를 입력해주세요!");
		};
	}
}
