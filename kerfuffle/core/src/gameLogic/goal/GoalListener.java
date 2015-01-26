package gameLogic.goal;


import Util.Tuple;

import java.util.List;

public interface GoalListener {
    public void stationReached(List<Tuple<String,Integer>> history);
}
