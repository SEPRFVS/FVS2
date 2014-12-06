package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gameLogic.map.Connection;
import gameLogic.map.IPositionable;
import gameLogic.map.Map;
import gameLogic.map.Station;

public class GameScreen extends ScreenAdapter {
    final private TaxeGame game;
    private OrthographicCamera camera;
    private Stage stage; // Stage - Holds all actors
    private Group actors; //Group - Hold all actors to allow searching and redrawing
    private Vector3 touchPoint;
    private Texture mapTexture;
    private Image mapImage;
    private Map map;


    public GameScreen(TaxeGame game) {
        this.game = game;

        camera = new OrthographicCamera(TaxeGame.WIDTH, TaxeGame.HEIGHT);
        camera.setToOrtho(false); // Makes the origin to be in the lower left corner
        stage = new Stage();
        actors = stage.getRoot();
        touchPoint = new Vector3();
        mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
        mapImage = new Image(mapTexture);

        map = new Map();
    }


    //Display all stations
    public void renderStations() {
        for (Station station : map.getStations()) {
            renderStation(station);
        }
    }

    //Display individual station
    public void renderStation(Station station) {
        IPositionable location = station.getLocation();
        MapActor actor = new MapActor(location.getX(), location.getY(), station);
        actor.setTouchable(Touchable.enabled);
        actors.addActor(actor);
    }


    //Display all connections
    public void renderConnections() {
        for (Connection connection : map.getConnections()) {
            renderConnection(connection);
        }
    }

    //Display Individual connection
    public void renderConnection(Connection connection) {
        IPositionable lowerCorner, upperCorner;
        if (connection.getStation1().getLocation().getX() < connection.getStation2().getLocation().getY()) {
            //Lower position at X
            lowerCorner = connection.getStation1().getLocation();
            upperCorner = connection.getStation2().getLocation();
        } else {
            //Lower position at Y
            lowerCorner = connection.getStation2().getLocation();
            upperCorner = connection.getStation1().getLocation();
        }
        MapActor actor = new MapActor(lowerCorner.getX(), lowerCorner.getY(), connection);
        actor.expandConnection(lowerCorner, upperCorner);
        actors.addActor(actor);
    }


    //Render Screen on load
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    // Called when GameScreen becomes current screen of the game
    public void show() {
        //Create GameScreen Image
        Gdx.input.setInputProcessor(stage);
        mapImage.setWidth(TaxeGame.WIDTH);
        mapImage.setHeight(TaxeGame.HEIGHT);
        stage.addActor(mapImage);

        renderStations();
        renderConnections();
    }


    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        mapTexture.dispose();
        stage.dispose();
    }

}