package gameLogic;

import gameLogic.goal.Goal;
import gameLogic.goal.GoalManager;
import gameLogic.resource.Resource;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private PlayerManager pm;
    private int score;
    private List<Resource> resources;
    private List<Goal> goals;

    public Player(PlayerManager pm) {
        goals = new ArrayList<Goal>();
        resources = new ArrayList<Resource>();
        this.pm = pm;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void addResource(Resource resource) {
        resources.add(resource);
        changed();
    }

    public void removeResource(Resource resource) {
        resources.remove(resource);
        resource.dispose();
        changed();
    }

    public void addGoal(Goal goal) {
        if (goals.size() >= GoalManager.CONFIG_MAX_PLAYER_GOALS) {
            throw new RuntimeException("Max player goals exceeded");
        }

        goals.add(goal);
        changed();
    }
    
    public void completeGoal(Goal goal){
    	addScore(goal.getRewardScore());
    	removeGoal(goal);
    }
    
    public void removeGoal(Goal goal){
    	goals.remove(goal);
    	changed();
    }

    /**
     * Method is called whenever a property of this player changes, or one of the player's resources changes
     */
    public void changed() {
        pm.playerChanged();
    }

    public List<Goal> getGoals() {
        return goals;
    }
}
