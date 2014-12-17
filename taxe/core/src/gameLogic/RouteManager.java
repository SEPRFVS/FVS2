package gameLogic;


import gameLogic.map.IPositionable;
import gameLogic.resource.Train;

public class RouteManager {
    public static final int ANIMATION_DURATION = 5;

    public void moveTrainPerTurn(Train train){
        for (IPositionable nextStop : train.getRoute()){
            //todo
        }

    }
}
