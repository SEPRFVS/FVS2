package gameLogic.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.CollisionStation;
import gameLogic.map.Map;
import gameLogic.map.Station;
import gameLogic.resource.Train;
import gameLogic.resource.TrainHelper;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 3;

	private Goal generateRandom(int turn){
		Map map = Game.getInstance().getMap();
		Station origin;
		do{
			origin = map.getRandomStation();
		} while (origin instanceof CollisionStation);
		Station destination;
		do {
			destination = map.getRandomStation();
		} while (destination == origin || destination instanceof CollisionStation);
		
		Goal goal = new Goal(origin, destination, turn);
		Random random = new Random();
		if(random.nextInt(2) == 1){
			goal.addConstraint("train", TrainHelper.getTrainNames().get(random.nextInt(TrainHelper.getTrainNames().size())));
		}

		return goal;
	}
	
	//TODO is there a better way as supposed to passing in the player?
	public void givePlayerGoal(Player player){
		player.addGoal(generateRandom(player.getPlayerManager().getTurnNumber()));
	}

	public ArrayList<String> trainArrived(Train train, Player player){
		//TODO fancy goal checking stuff
		ArrayList<String> completedString = new ArrayList<String>();
		for(Goal goal:player.getGoals()){
			if(goal.isComplete(train)){
				player.completeGoal(goal);
				player.removeResource(train);
				completedString.add("Player " + player.getPlayerNumber() + " completed a goal to " + goal.toString() + "!");
			}
		}
		System.out.println("Train arrived to final destination: " + train.getFinalDestination().getName());
		return completedString;
	}
}
