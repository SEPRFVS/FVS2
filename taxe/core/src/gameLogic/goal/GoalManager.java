package gameLogic.goal;

import gameLogic.Player;
import gameLogic.resource.Train;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 7;

	private Goal generateRandom() {
		return new GoalDummy();
	}
	
	public void givePlayerGoal(Player player) {
		player.addGoal(generateRandom());
	}

	public void trainArrived(Train train, Player player) {
		//Todo fancy goal checking stuff
		System.out.println("Train arrived to final destination: " + train.getFinalDestination().getName());
	}
}
