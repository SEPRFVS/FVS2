package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import fvs.taxe.controller.*;
import fvs.taxe.dialog.DialogEndGame;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.GameStateListener;
import gameLogic.PlayerChangedListener;
import gameLogic.TurnListener;
import gameLogic.map.Map;


public class GameScreen extends ScreenAdapter {
    final private TaxeGame game;
    private Stage stage;
    private Texture mapTexture;
    private Game gameLogic;
    private Skin skin;
    private Map map;
    private float timeAnimated = 0;
    public static final int ANIMATION_TIME = 2;
    private Tooltip tooltip;
    private Context context;

    private StationController stationController;
    private TopBarController topBarController;
    private ResourceController resourceController;
    private GoalController goalController;
    private RouteController routeController;

    public GameScreen(TaxeGame game) {
        this.game = game;
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        gameLogic = Game.getInstance();
        context = new Context(stage, skin, game, gameLogic);
        Gdx.input.setInputProcessor(stage);

        mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
        map = gameLogic.getMap();

        tooltip = new Tooltip(skin);
        stage.addActor(tooltip);

        stationController = new StationController(context, tooltip);
        topBarController = new TopBarController(context);
        resourceController = new ResourceController(context);
        goalController = new GoalController(context);
        routeController = new RouteController(context);

        context.setRouteController(routeController);
        context.setTopBarController(topBarController);
        context.setStationController(stationController);

        gameLogic.getPlayerManager().subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                gameLogic.setState(GameState.ANIMATING);
                topBarController.displayFlashMessage("Time is passing...", Color.BLACK);
            }
        });
        gameLogic.subscribeStateChanged(new GameStateListener() {
        	@Override
        	public void changed(GameState state){
        		if(gameLogic.getPlayerManager().getTurnNumber() == gameLogic.TOTAL_TURNS && state == GameState.NORMAL) {
        			DialogEndGame dia = new DialogEndGame(GameScreen.this.game, gameLogic.getPlayerManager(), skin);
        			dia.show(stage);
        		}
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

        topBarController.drawBackground();

        stationController.renderConnections(map.getConnections(), Color.GRAY);

        if(gameLogic.getState() == GameState.ROUTING) {
            routeController.drawRoute(Color.BLACK);
        }

        if(gameLogic.getState() == GameState.ANIMATING) {
            timeAnimated += delta;
            if (timeAnimated >= ANIMATION_TIME) {
                gameLogic.setState(GameState.NORMAL);
                timeAnimated = 0;
            }
        }
        
        if(gameLogic.getState() == GameState.NORMAL || gameLogic.getState() == GameState.PLACING){
        	stationController.displayStations();
        }

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        game.batch.begin();
        game.fontSmall.draw(game.batch, "Turn " + (gameLogic.getPlayerManager().getTurnNumber() + 1), (float) TaxeGame.WIDTH - 70.0f, 20.0f);
        game.batch.end();

        resourceController.drawHeaderText();
        goalController.showCurrentPlayerGoals();
    }

    @Override
    // Called when GameScreen becomes current screen of the game
    public void show() {
        stationController.renderStations();
        topBarController.addEndTurnButton();
        resourceController.drawPlayerResources(gameLogic.getPlayerManager().getCurrentPlayer());
    }


    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        mapTexture.dispose();
        stage.dispose();
    }

}