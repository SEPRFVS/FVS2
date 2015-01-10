package fvs.taxe.controller;

import java.util.ArrayList;
import java.util.List;

import Util.Tuple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.CollisionStation;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class TrainController {
    public static final int TRAIN_OFFSET = 8;

    private Context context;

    public TrainController(Context context) {
        this.context = context;
    }

    public Image renderTrain(Train train) {
        TrainActor trainActor = new TrainActor(train);
        trainActor.addListener(new TrainClicked(context, train));
        context.getStage().addActor(trainActor);

        return trainActor;
    }

    public void addMoveActions(final Train train) {
        SequenceAction action = Actions.sequence();
        IPositionable current = train.getPosition();

        for (final Station station : train.getRoute()) {
            IPositionable next = station.getLocation();
            float duration = Vector2.dst(current.getX(), current.getY(), next.getX(), next.getY()) / train.getSpeed();
            action.addAction(moveTo(next.getX() - TRAIN_OFFSET, next.getY() - TRAIN_OFFSET, duration));
            action.addAction(new RunnableAction() {
                public void run() {
                    train.addHistory(station.getName(), context.getGameLogic().getPlayerManager().getTurnNumber());
                    System.out.println("Added to history: passed " + station.getName() + " on turn "
                            + context.getGameLogic().getPlayerManager().getTurnNumber());
                    
                    //test for train collisions at Junction point
                    if(station instanceof CollisionStation){
                    	boolean collision = false;
                    	List<Tuple<Player, Train>> trainsToDestroy = new ArrayList<Tuple<Player, Train>>();
                    	for(Player player : context.getGameLogic().getPlayerManager().getAllPlayers()){
                    		for(Resource resource : player.getResources()){
                    			if(resource instanceof Train){
                    				Train otherTrain = (Train) resource;
                    				if(otherTrain.getActor() != null){
                    					if(otherTrain.getActor().getX() < train.getActor().getX() + 30 && otherTrain.getActor().getX() > train.getActor().getX() - 30 && otherTrain.getActor().getY() < train.getActor().getY() + 30 && otherTrain.getActor().getY() > train.getActor().getY() - 30 && otherTrain != train){
                    						collision = true;
                    						//destroy trains that have crashed and burned
                    						trainsToDestroy.add(new Tuple<Player, Train>(context.getGameLogic().getPlayerManager().getCurrentPlayer(),train));
                    						trainsToDestroy.add(new Tuple<Player, Train>(player,otherTrain));
                    					}
                    				}
                    			}
                    		}
                    	}
                    	if(collision){
                    		for(Tuple<Player, Train> destroy : trainsToDestroy){
                    			destroy.getSecond().getActor().clearActions();
                    			destroy.getFirst().removeResource(destroy.getSecond());
                    		}
                    		//TODO Maybe have a dialog box as it's quite important
                    		context.getTopBarController().displayFlashMessage("Two trains collided at a Junction.  They were both destoryed.", Color.RED);
                    	}
                    }
                }
            });
            current = next;
        }

        final IPositionable finalPosition = current;

        action.addAction(new RunnableAction(){
            public void run(){
                ArrayList<String> completedGoals = context.getGameLogic().getGoalManager().trainArrived(train, train.getPlayer());
                for(String message : completedGoals){
                	context.getTopBarController().displayFlashMessage(message, Color.WHITE);
                }
                train.setFinalDestination(null);
                train.setPosition(finalPosition);
            }
        });

        // Remove previous actions if any and add new sequential action
        train.getActor().clearActions();
        train.getActor().addAction(action);
    }
}
