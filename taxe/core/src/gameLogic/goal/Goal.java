package gameLogic.goal;

import Util.Tuple;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class Goal {
	private Station origin;
	private Station destination;
	private int turnIssued;
	private boolean complete = false;
	//constraints
	private String trainName = null;
	
	public Goal(Station origin, Station destination, int turn) {
		this.origin = origin;
		this.destination = destination;
		this.turnIssued = turn;
	}
	
	public void addConstraint(String name, String value) {
		if(name.equals("train")) {
			trainName = value;
		} else {
			throw new RuntimeException(name + " is not a valid goal constraint");
		}
	}

	public boolean isComplete(Train train) {
		boolean passedOrigin = false;
		for(Tuple<String, Integer> history: train.getHistory()) {
			if(history.getFirst().equals(origin.getName()) && history.getSecond() >= turnIssued) {
				passedOrigin = true;
			}
		}
		if(train.getFinalDestination() == destination && passedOrigin) {
			if(trainName == null || trainName.equals(train.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	//TODO Rename to be more descriptive of function (Doesn't represent entire goal)
	public String toString() {
		String trainString = "train";
		if(trainName != null) {
			trainString = trainName;
		}
		return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName();
	}

	public void setComplete() {
		complete = true;
	}

	public boolean getComplete() {
		return complete;
	}
}