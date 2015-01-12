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

    public void addMoveActions(final Train train) {
        SequenceAction action = Actions.sequence();
        IPositionable current = train.getPosition();
        action.addAction(new RunnableAction() {
        	public void run() {
        		train.getActor().setVisible(true);
        	}
        });

        for (final Station station : train.getRoute()) {
        	action.addAction(new RunnableAction() {
        		public void run() {
        			train.setPosition(new Position(0,0));
        		}
        	});
            IPositionable next = station.getLocation();
            float duration = Vector2.dst(current.getX(), current.getY(), next.getX(), next.getY()) / train.getSpeed();
            action.addAction(moveTo(next.getX() - TrainActor.width / 2, next.getY() - TrainActor.height / 2, duration));
            action.addAction(new RunnableAction() {
                public void run() {
                    train.addHistory(station.getName(), context.getGameLogic().getPlayerManager().getTurnNumber());
                    System.out.println("Added to history: passed " + station.getName() + " on turn "
                            + context.getGameLogic().getPlayerManager().getTurnNumber());
                    train.setPosition(station.getLocation());
                    
                    //test for train collisions at Junction point
                    if(station instanceof CollisionStation) {
                    	boolean collision = false;
                    	List<Train> trainsToDestroy = new ArrayList<Train>();
                    	for(Player player : context.getGameLogic().getPlayerManager().getAllPlayers()) {
                    		for(Resource resource : player.getResources()) {
                    			if(resource instanceof Train) {
                    				Train otherTrain = (Train) resource;
                    				if(otherTrain.getActor() == null) continue;
                                    if(otherTrain == train) continue;

                                    if(train.getActor().getBounds().overlaps(otherTrain.getActor().getBounds())) {
                                        collision = true;
                                        //destroy trains that have crashed and burned
                                        trainsToDestroy.add(train);
                                        trainsToDestroy.add(otherTrain);
                                    }

                    			}
                    		}
                    	}
                    	if(collision) {
                    		for(Train trainToDestroy : trainsToDestroy) {
                    			trainToDestroy.getActor().clearActions();
                                trainToDestroy.getActor().remove();
                    			trainToDestroy.getPlayer().removeResource(trainToDestroy);
                    		}
                    		//TODO Maybe have a dialog box as it's quite important
                    		context.getTopBarController().displayFlashMessage("Two trains collided at a Junction.  They were both destroyed.", Color.RED, 2);
                    	}
                    }
                }
            });
            current = next;
        }

        final IPositionable finalPosition = current;

        action.addAction(new RunnableAction() {
            public void run() {
                ArrayList<String> completedGoals = context.getGameLogic().getGoalManager().trainArrived(train, train.getPlayer());
                for(String message : completedGoals) {
                	context.getTopBarController().displayFlashMessage(message, Color.WHITE, 2);
                }
                System.out.println(train.getFinalDestination().getLocation().getX() + "," + train.getFinalDestination().getLocation().getY());
                train.setPosition(train.getFinalDestination().getLocation());
                train.getActor().setVisible(false);
                train.setFinalDestination(null);
            }
        });

        // Remove previous actions if any and add new sequential action
        train.getActor().clearActions();
        train.getActor().addAction(action);
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
