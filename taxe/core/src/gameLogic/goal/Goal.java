package gameLogic.goal;

import Util.Tuple;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class Goal {
	private int rewardScore;
	private Station origin;
	private Station destination;
	private int turnIssued;
	//TODO Add in constraints
	
	public Goal(Station origin, Station destination, int score, int turn){
		this.origin = origin;
		this.destination = destination;
		this.rewardScore = score;
		this.turnIssued = turn;
	}

	public int getRewardScore() {
		return rewardScore;
	}

	public boolean isComplete(Train train){
		boolean passedOrigin = false;
		for(Tuple<String, Integer> history: train.getHistory()){
			if(history.getFirst() == origin.getName() && history.getSecond() >= turnIssued){
				passedOrigin = true;
			}
		}
		if(train.getFinalDestination() == destination && passedOrigin){
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