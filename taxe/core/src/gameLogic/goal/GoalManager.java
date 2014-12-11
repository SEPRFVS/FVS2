package gameLogic.goal;

import gameLogic.Player;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 7;

	private Goal GenerateRandom() {
		return new GoalDummy();
	}
	
	public void GivePlayerGoal(Player p) {
		p.addGoal(GenerateRandom());
	}
}
