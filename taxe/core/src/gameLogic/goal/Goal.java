package gameLogic.goal;

import gameLogic.Player;

public abstract class Goal {
	protected int rewardScore;
	
	abstract public boolean isComplete();
	
	public void awardScore(Player p) {
		p.addScore(rewardScore);
	}
}