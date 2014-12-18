package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
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

    private Stage stage;
    private TaxeGame game;
    private Map map;
    private Skin skin;
    /*
     have to use CopyOnWriteArrayList because when we iterate through our listeners and execute
     their handler's method, one case unsubscribes from the event removing itself from this list
     and this list implementation supports removing elements whilst iterating through it
      */
    private List<StationClickListener> stationClickListeners = new CopyOnWriteArrayList<StationClickListener>();

    public MapRenderer(TaxeGame game, Stage stage, Skin skin) {
        this.game = game;
        this.stage = stage;
        this.skin = skin;

        map = new Map();
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

    private void renderStation(final Station station) {
        IPositionable location = station.getLocation();
        MapActor actor = new MapActor(location.getX(), location.getY(), station);
        actor.setTouchable(Touchable.enabled);
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                /*
                 if a station is clicked, someone could be attempting to place a train there,
                 or be routing a train, we don't care about this, we should just publish an event
                  */
                stationClicked(station);
            }
        });
        stage.addActor(actor);
    }

    public void renderConnections() {
        int lineWidth = 5;
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.GRAY);
        //game.shapeRenderer.setProjectionMatrix(camera.combined);

        for (Connection connection : map.getConnections()) {
            IPositionable start = connection.getStation1().getLocation();
            IPositionable end = connection.getStation2().getLocation();
            game.shapeRenderer.rectLine(start.getX(), start.getY(), end.getX(), end.getY(), lineWidth);
        }
        game.shapeRenderer.end();
    }

    public Image renderTrain(Train t) {
        Image trainImage = new Image(new Texture(Gdx.files.internal(t.getImage())));
        IPositionable position = t.getPosition();
        trainImage.setSize(30f, 30f);
        trainImage.setPosition(position.getX() - OFFSET, position.getY() - OFFSET);
        trainImage.addListener(new TrainClicked(t, skin, this, stage));


        //train.addAction(sequence(moveTo(340f, 290f, 5f), moveTo(560, 390, 5f), moveTo(245, 510, 5f)));
        t.setActor(trainImage);
        stage.addActor(trainImage);

        List<Station> route = new ArrayList<Station>();
        route.add(new Station("Test", new Position(200, 200)));
        route.add(new Station("Test", new Position(300, 300)));
        t.setRoute(route);

        return trainImage;
    }

    public static void moveTrain(Train train, IPositionable target, float sec){
        if (train.getActor() == null) return;
        train.getActor().addAction(moveTo(target.getX() - OFFSET, target.getY() - OFFSET, sec));
        train.setPosition(new Position(target.getX(), target.getY()));
    }


    public void moveTrainByTurn(Train train){
        int stationsToBeRemoved = 0;
        int totalDst = train.getSpeed();
        int availableDst = totalDst;
        IPositionable current = train.getPosition();

        if (current == null) return;
        if (train.getRoute() == null || train.getRoute().size() == 0) return;

        IPositionable next;
        for (Station station : train.getRoute()){
            next = station.getLocation();
            float distanceToNext = Vector2.dst(current.getX(), current.getY(), next.getX(), next.getY());

            if (distanceToNext <= availableDst) {
                float sec = distanceToNext / totalDst * ANIMATION_DURATION;
                MapRenderer.moveTrain(train, next, sec);
                availableDst -= distanceToNext;
                current = next;
                stationsToBeRemoved++;
                train.addHistory(station.getName(), Game.getInstance().getPlayerManager().getTurnNumber());
            } else {
                int delta_x = Math.round((availableDst / distanceToNext) * (next.getX() - current.getX()));
                int delta_y = Math.round((availableDst / distanceToNext) * (next.getY() - current.getY()));

                IPositionable targetLocation = new Position(current.getX() + delta_x, current.getY() + delta_y);
                float dist = Vector2.dst(current.getX(), current.getY(), targetLocation.getX(), targetLocation.getY());
                float sec = dist / totalDst * ANIMATION_DURATION;
                MapRenderer.moveTrain(train, targetLocation, sec);
                break;
            }
        }

        if (stationsToBeRemoved > 0){
            train.setRoute(train.getRoute().subList(stationsToBeRemoved, train.getRoute().size()));
        }

    }
}
