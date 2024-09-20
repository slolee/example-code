import java.util.List;

public class BaseballGameWithZero extends BaseballGame {

	public BaseballGameWithZero() {
		super(List.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));
	}

	public BaseballGameWithZero(int size) {
		super(size, List.of('1', '2', '3', '4', '5', '6', '7', '8', '9', '0'));
	}

	@Override
	public String getResult() {
		return "[0포함 모드] " + super.getResult();
	}
}
