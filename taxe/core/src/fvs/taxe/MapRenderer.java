package fvs.taxe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.actor.StationActor;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.*;
import gameLogic.resource.Train;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;


public class MapRenderer {
    public static final int TRAIN_OFFSET = 8;
    public static final int ANIMATION_TIME = 2;
    private final int LINE_WIDTH = 5;

    private Stage stage;
    private TaxeGame game;
    private Map map;
    private Skin skin;
    private List<IPositionable> placingPositions;
    private Group routingButtons = new Group();
    private Tooltip tooltip;
    /*
     have to use CopyOnWriteArrayList because when we iterate through our listeners and execute
     their handler's method, one case unsubscribes from the event removing itself from this list
     and this list implementation supports removing elements whilst iterating through it
      */
    private List<StationClickListener> stationClickListeners = new CopyOnWriteArrayList<StationClickListener>();

    public MapRenderer(TaxeGame game, Stage stage, Skin skin, Map map) {
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.map = map;

        tooltip = new Tooltip(skin);
        stage.addActor(tooltip);
    }

    public Map getMap() {
        return map;
    }

    public void setPlacingPositions(List<IPositionable> placingPositions) {
        this.placingPositions = placingPositions;
    }

    public List<IPositionable> getPlacingPositions() {
        return placingPositions;
    }

    public void subscribeStationClick(StationClickListener listener) {
        stationClickListeners.add(listener);
    }

    public void unsubscribeStationClick(StationClickListener listener) {
        stationClickListeners.remove(listener);
    }

    private void stationClicked(Station station) {
        for (StationClickListener listener : stationClickListeners) {
            listener.clicked(station);
        }
    }

    public void renderStations() {
        for (Station station : map.getStations()) {
            renderStation(station);
        }
    }

    public void beginRoutingState(RouteConfirmedListener listener, List<IPositionable> initialPositions) {
        setPlacingPositions(initialPositions);
        Game.getInstance().setState(GameState.ROUTING);
        addRoutingButtons(listener);
    }

    private void addRoutingButtons(final RouteConfirmedListener listener) {
        TextButton doneRouting = new TextButton("Route Complete", skin);
        TextButton cancel = new TextButton("Cancel", skin);

        doneRouting.setPosition(500, 500);
        cancel.setPosition(500, 450);

        cancel.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                endRouting();
            }
        });

        doneRouting.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                listener.confirmed();
                endRouting();
            }
        });

        routingButtons.addActor(doneRouting);
        routingButtons.addActor(cancel);

        stage.addActor(routingButtons);
    }

    private void endRouting() {
        Game.getInstance().setState(GameState.NORMAL);
        routingButtons.remove();
    }

    private void renderStation(final Station station) {
        final StationActor stationActor = new StationActor(station.getLocation());

        stationActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stationClicked(station);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tooltip.setPosition(stationActor.getX() + 20, stationActor.getY() + 20);
                tooltip.show(station.getName());
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tooltip.hide();
            }
        });

        stage.addActor(stationActor);
    }

    public void drawRoute(List<IPositionable>positions, Color color) {
        IPositionable previousPosition = null;
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(color);

        for(IPositionable position : positions) {
            if(previousPosition != null) {
                game.shapeRenderer.rectLine(previousPosition.getX(), previousPosition.getY(), position.getX(),
                        position.getY(), LINE_WIDTH);
            }

            previousPosition = position;
        }

        game.shapeRenderer.end();
    }

    public void renderConnections(List<Connection> connections, Color color) {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(color);
        //game.shapeRenderer.setProjectionMatrix(camera.combined);

        for (Connection connection : connections) {
            IPositionable start = connection.getStation1().getLocation();
            IPositionable end = connection.getStation2().getLocation();
            game.shapeRenderer.rectLine(start.getX(), start.getY(), end.getX(), end.getY(), LINE_WIDTH);
        }
        game.shapeRenderer.end();
    }

    public Image renderTrain(Train train) {
        TrainActor trainActor = new TrainActor(train);
        trainActor.addListener(new TrainClicked(train, skin, this, stage));
        stage.addActor(trainActor);

        return trainActor;
    }

    public void addMoveActions(final Train train) {
        SequenceAction action = Actions.sequence();
        IPositionable current = train.getPosition();

        for (Station station : train.getRoute()) {
            IPositionable next = station.getLocation();
            float duration = Vector2.dst(current.getX(), current.getY(), next.getX(), next.getY()) / train.getSpeed();
            action.addAction(moveTo(next.getX(), next.getY(), duration));
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
