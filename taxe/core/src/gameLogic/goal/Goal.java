package gameLogic.goal;

import gameLogic.map.Station;
import gameLogic.resource.Train;

public class Goal {
	private int rewardScore;
	private Station origin;
	private Station destination;
	//TODO Add in constraints
	
	public Goal(Station origin, Station destination, int score){
		this.origin = origin;
		this.destination = destination;
		this.rewardScore = score;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public boolean isComplete(Train train){
		if(train.getFinalDestination() == destination){
			return true;
		}else{
			return false;
		}
	}
	
	//TODO Rename to be more descriptive of function (Doesn't represent entire goal)
	//TODO represent goals as boxes instead of strings (Code needed in MapRenderer)
	public String toString(){
		return "Send a train from " + origin.getName() + " to " + destination.getName() + " - " + rewardScore + " points";
	}
	
}