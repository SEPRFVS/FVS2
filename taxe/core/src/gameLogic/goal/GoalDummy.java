package gameLogic.goal;

public class GoalDummy extends Goal {
	public GoalDummy() {
		rewardScore = 10;
	}

	@Override
	public boolean isComplete() {
		return true;
	}
}
