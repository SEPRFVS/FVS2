package gameLogic;

import gameLogic.goal.Goal;
import gameLogic.resource.Resource;

import java.util.List;

public class Player {
	private int score;
	private List<Resource> resources;
	private List<Goal> goals;
	
	public int getScore() {
		return score;
	}
	
	public List<Resource> getResources() {
		return resources;
	}
}
