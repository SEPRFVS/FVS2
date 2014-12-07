package gameLogic.goal;

import gameLogic.Player;

public class GoalManager {
	private Goal GenerateRandom() {
		return new GoalDummy();
	}
	
	public void GivePlayerGoal(Player p) {
		p.addGoal(GenerateRandom());
	}
}
