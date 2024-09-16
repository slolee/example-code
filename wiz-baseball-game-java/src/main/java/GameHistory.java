import java.util.ArrayList;
import java.util.List;

public class GameHistory {

	private final List<BaseballGame> histories = new ArrayList<>();

	public void record(BaseballGame game) {
		histories.add(game);
	}

	public void show() {
		System.out.println("\n< 게임 기록 보기 >");
		for (int i = 0; i < histories.size(); i++) {
			System.out.println((i + 1) + "번째 게임 : " + histories.get(i).getResult());
		}
		System.out.println();
	}
}
