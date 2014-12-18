package gameLogic.goal;

import gameLogic.Player;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 7;

	private Goal generateRandom() {
		return new GoalDummy();
	}
	
	public void givePlayerGoal(Player player) {
		player.addGoal(generateRandom());
	}
}
