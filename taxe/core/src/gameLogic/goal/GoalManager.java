package gameLogic.goal;

import java.util.List;
import java.util.Random;

import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 7;

	private Goal generateRandom(List<Station> stations, int turn){
		Random rand = new Random();
		Station origin = stations.get(rand.nextInt((stations.size()-1)+1));
		Station destination = null;
		while(origin == destination || destination == null){
			destination = stations.get(rand.nextInt((stations.size()-1)+1));
		}
		return new Goal(origin, destination, 10, turn);
		//return new GoalDummy();
	}
	
	//TODO is there a better way as supposed to passing in the player?
	public void givePlayerGoal(Player player, List<Station> stations){
		player.addGoal(generateRandom(stations,player.getPlayerManager().getTurnNumber()));
	}

	public void trainArrived(Train train, Player player){
		//TODO fancy goal checking stuff
		//for(Goal goal:player.getGoals()){
		//	if(goal.isComplete(train)){
		//		player.completeGoal(goal);
		//	}
		//}
		System.out.println("Train arrived to final destination: " + train.getFinalDestination().getName());
	}
}
