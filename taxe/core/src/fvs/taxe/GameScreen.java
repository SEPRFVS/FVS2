package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.*;
import gameLogic.goal.Goal;
import gameLogic.map.Map;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends ScreenAdapter {
    final private TaxeGame game;
    private OrthographicCamera camera;
    private Stage stage; // Stage - Holds all actors
    private Texture mapTexture;
    private Game gameLogic;
    private Skin skin;
    private MapRenderer mapRenderer;
    private Map map;
    private float timeAnimated = 0;

    public GameScreen(TaxeGame game) {
        this.game = game;

        //camera = new OrthographicCamera(TaxeGame.WIDTH, TaxeGame.HEIGHT);
        //camera.setToOrtho(false); // Makes the origin to be in the lower left corner
        stage = new Stage();
        mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));


        Gdx.input.setInputProcessor(stage);

        // game logic stuff
        gameLogic = Game.getInstance();


        map = gameLogic.getMap();
        mapRenderer = new MapRenderer(game, stage, skin, map);

        gameLogic.getPlayerManager().subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                Game.getInstance().setState(GameState.ANIMATING);
            }
        });


    }



    // called every frame
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(mapTexture, 0, 0);
        game.batch.end();

        controlsBackground();

        mapRenderer.renderConnections(map.getConnections(), Color.GRAY);

        if(gameLogic.getState() == GameState.ROUTING) {
            mapRenderer.drawRoute(mapRenderer.getPlacingPositions(), Color.BLACK);
        }

        if(gameLogic.getState() == GameState.ANIMATING) {
            timeAnimated += delta;
            if (timeAnimated >= MapRenderer.ANIMATION_TIME) {
                gameLogic.setState(GameState.NORMAL);
                timeAnimated = 0;
            }
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


        // text must be rendered after the stage so the bg image doesn't overlap
        debugKeys();

        drawResourcesHeader();
        showCurrentPlayerGoals();
    }

    @Override
    // Called when GameScreen becomes current screen of the game
    public void show() {
        mapRenderer.renderStations();
        addEndTurnButton();
        showCurrentPlayerResources();
    }


    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        mapTexture.dispose();
        stage.dispose();
    }

}