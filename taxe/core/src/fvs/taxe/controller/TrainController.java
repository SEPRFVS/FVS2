package fvs.taxe.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;
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
                    train.addHistory(station.getName(), Game.getInstance().getPlayerManager().getTurnNumber());
                    System.out.println("Added to history: passed " + station.getName() + " on turn "
                            + Game.getInstance().getPlayerManager().getTurnNumber());
                }
            });
            current = next;
        }

        final IPositionable finalPosition = current;

        action.addAction(new RunnableAction(){
            public void run(){
                Game.getInstance().getGoalManager().trainArrived(train, train.getPlayer());
                train.setFinalDestination(null);
                train.setPosition(finalPosition);
            }
        });

        // Remove previous actions if any and add new sequential action
        train.getActor().clearActions();
        train.getActor().addAction(action);
    }
}
