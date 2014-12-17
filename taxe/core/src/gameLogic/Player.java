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

    public void addResource(Resource r) {
        resources.add(r);
        pm.playerChanged();
    }

    public void removeResource(Resource r) {
        resources.remove(r);
        r.dispose();
        pm.playerChanged();
    }

    public void addGoal(Goal g) {
        if (goals.size() >= GoalManager.CONFIG_MAX_PLAYER_GOALS) {
            throw new RuntimeException("Max player goals exceeded");
        }

        goals.add(g);
        pm.playerChanged();
    }

    public List<Goal> getGoals() {
        return goals;
    }
}
