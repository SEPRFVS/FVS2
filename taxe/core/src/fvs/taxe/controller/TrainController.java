package fvs.taxe.controller;

import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

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
                    if(((Train) resource).getActor() != null && resource != train && !trainAtStation) {
                        ((Train) resource).getActor().setVisible(visible);
                    }
                }
            }
        }
    }
}
