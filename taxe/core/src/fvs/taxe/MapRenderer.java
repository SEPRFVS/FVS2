package fvs.taxe;

import Util.Tuple;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.map.*;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;


public class MapRenderer {
    public static final int OFFSET = 8;
    public static final int ANIMATION_DURATION = 5;
    private final int LINE_WIDTH = 5;

    private Stage stage;
    private TaxeGame game;
    private Map map;
    private Skin skin;
    private List<IPositionable> placingPositions;
    private Group routingButtons = new Group();
    private Window tooltip;
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

        tooltip = new Window("", skin);
        //tooltip.add(new Label("TESTING TOOLTIP", skin));
        tooltip.setSize(150, 20);
        tooltip.setVisible(false);

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
        int width = 35;
        int height = 22;

        IPositionable location = station.getLocation();
        final Image stationActor = new Image(new Texture(Gdx.files.internal("station_icon.png")));
        stationActor.setPosition(location.getX() - width / 2, location.getY() - height / 2);
        stationActor.setSize(width, height);
        stationActor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*
                 if a station is clicked, someone could be attempting to place a train there,
                 or be routing a train, we don't care about this, we should just publish an event
                  */
                System.out.println(station.getName());
                stationClicked(station);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tooltip.setTitle(station.getName());
                tooltip.setVisible(true);
                tooltip.setPosition(stationActor.getX() + 20, stationActor.getY() + 20);
                tooltip.toFront();
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tooltip.setVisible(false);
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
        Image trainImage = new Image(new Texture(Gdx.files.internal(train.getImage())));
        IPositionable position = train.getPosition();
        trainImage.setSize(30f, 30f);
        trainImage.setPosition(position.getX() - OFFSET, position.getY() - OFFSET);
        trainImage.addListener(new TrainClicked(train, skin, this, stage));

        //train.addAction(sequence(moveTo(340f, 290f, 5f), moveTo(560, 390, 5f), moveTo(245, 510, 5f)));
        train.setActor(trainImage);
        stage.addActor(trainImage);

        return trainImage;
    }

    public static void moveTrain(final Train train, List<Tuple<IPositionable, Float>> targets, final Player player, boolean lastTrain){
        if (train.getActor() == null || targets == null) return;

        //Sequential action of all of the trains' moveTo actions for one turn
        SequenceAction action = Actions.sequence();

        action.addAction(new RunnableAction(){
            public void run(){
                Game.getInstance().setState(GameState.ANIMATING);
            }
        });

        for (Tuple<IPositionable, Float> target : targets) {
            action.addAction(moveTo(target.getFirst().getX() - OFFSET, target.getFirst().getY() - OFFSET, target.getSecond()));
            train.setPosition(new Position(target.getFirst().getX(), target.getFirst().getY()));

            if (train.getFinalDestination().getLocation() == target.getFirst()) {
                action.addAction(new RunnableAction(){
                    public void run(){
                        Game.getInstance().getGoalManager().trainArrived(train, player);
                        train.setFinalDestination(null);
                    }
                });
            }
        }

        if (lastTrain) {
            action.addAction(new RunnableAction(){
                public void run(){
                    Game.getInstance().setState(GameState.NORMAL);
                }
            });
        }

        train.getActor().addAction(action);
    }


    public void moveTrainByTurn(Train train, Player player, boolean lastTrain){
        int stationsToBeRemoved = 0;
        int totalDst = train.getSpeed();
        int availableDst = totalDst;
        IPositionable current = train.getPosition();

        // Hold moveTo target positions and animation durations in here
        List<Tuple<IPositionable, Float>> targets = new ArrayList<Tuple<IPositionable, Float>>();

        if (current == null) return;
        if (train.getRoute() == null || train.getRoute().size() == 0) return;

        IPositionable next;
        for (Station station : train.getRoute()){
            next = station.getLocation();
            float distanceToNext = Vector2.dst(current.getX(), current.getY(), next.getX(), next.getY());

            if (distanceToNext <= availableDst) {
                float sec = distanceToNext / totalDst * ANIMATION_DURATION;
                targets.add(new Tuple<IPositionable, Float>(next, sec));
                availableDst -= distanceToNext;
                stationsToBeRemoved++;
                current = next;
                train.addHistory(station.getName(), Game.getInstance().getPlayerManager().getTurnNumber());
            } else {
                int delta_x = Math.round((availableDst / distanceToNext) * (next.getX() - current.getX()));
                int delta_y = Math.round((availableDst / distanceToNext) * (next.getY() - current.getY()));

                IPositionable targetLocation = new Position(current.getX() + delta_x, current.getY() + delta_y);
                float dist = Vector2.dst(current.getX(), current.getY(), targetLocation.getX(), targetLocation.getY());
                float sec = dist / totalDst * ANIMATION_DURATION;
                targets.add(new Tuple<IPositionable, Float>(targetLocation, sec));
                break;
            }
        }

        if (stationsToBeRemoved > 0){
            train.setRoute(train.getRoute().subList(stationsToBeRemoved, train.getRoute().size()));
        }

        MapRenderer.moveTrain(train, targets, player, lastTrain);
    }
}
