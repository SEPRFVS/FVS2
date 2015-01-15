package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Player;
import gameLogic.map.CollisionStation;
import gameLogic.map.IPositionable;
import gameLogic.map.Position;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class TrainController {
    private Context context;

    public TrainController(Context context) {
        this.context = context;
    }

    public TrainActor renderTrain(Train train) {
        TrainActor trainActor = new TrainActor(train);
        trainActor.addListener(new TrainClicked(context, train));
        trainActor.setVisible(false);
        context.getStage().addActor(trainActor);

        return trainActor;
    }



    // Sets all trains on the map visible or invisible except one that we are routing for
    public void setTrainsVisible(Train train, boolean visible) {

        for(Player player : context.getGameLogic().getPlayerManager().getAllPlayers()) {
            for(Resource resource : player.getResources()) {
                if(resource instanceof Train) {
                	boolean trainAtStation = false;
                	for(Station station : context.getGameLogic().getMap().getStations()) {
                		if(station.getLocation() == ((Train) resource).getPosition()){
                			trainAtStation = true;
                			break;
                		}
                	}
                    if(((Train) resource).getActor() != null && resource != train && trainAtStation == false) {
                        ((Train) resource).getActor().setVisible(visible);
                    }
                }
            }
        }
    }
}
