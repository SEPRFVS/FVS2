package gameLogic.goal;

import java.util.List;
import java.util.Random;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.Map;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 7;

	private Goal generateRandom(int turn){
		Map map = Game.getInstance().getMap();
		Station origin = map.getRandomStation();
		Station destination;
		do {
			destination = map.getRandomStation();
		} while (destination == origin);

		return new Goal(origin, destination, 10, turn);
	}
	
	//TODO is there a better way as supposed to passing in the player?
	public void givePlayerGoal(Player player){
		player.addGoal(generateRandom(player.getPlayerManager().getTurnNumber()));
	}

	public void trainArrived(Train train, Player player){
		//TODO fancy goal checking stuff
		for(Goal goal:player.getGoals()){
			if(goal.isComplete(train)){
				player.completeGoal(goal);
			}
		}
		System.out.println("Train arrived to final destination: " + train.getFinalDestination().getName());
	}
}
