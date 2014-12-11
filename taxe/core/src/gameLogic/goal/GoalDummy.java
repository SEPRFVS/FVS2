package gameLogic.goal;

public class GoalDummy extends Goal {
	public GoalDummy() {
		rewardScore = 10;
	}

	@Override
	public boolean isComplete() {
		return true;
	}

	@Override
	public String toString() {
		return "string describing what this goal is...";
	}
}
