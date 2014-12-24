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
    private Group resourceButtons;
    private MapRenderer mapRenderer;
    private Map map;
    private float timeAnimated = 0;
    private TextButton endTurnButton;
    private Color controlsColor = Color.LIGHT_GRAY;
    private final int CONTROLS_HEIGHT = 40;

    public GameScreen(TaxeGame game) {
        this.game = game;

        //camera = new OrthographicCamera(TaxeGame.WIDTH, TaxeGame.HEIGHT);
        //camera.setToOrtho(false); // Makes the origin to be in the lower left corner
        stage = new Stage();
        mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        resourceButtons  = new Group();

        Gdx.input.setInputProcessor(stage);

        // game logic stuff
        gameLogic = Game.getInstance();
        gameLogic.getPlayerManager().subscribePlayerChanged(new PlayerChangedListener() {
            @Override
            public void changed() {
                showCurrentPlayerResources();
            }
        });

        map = gameLogic.getMap();
        mapRenderer = new MapRenderer(game, stage, skin, map);

        gameLogic.getPlayerManager().subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                Game.getInstance().setState(GameState.ANIMATING);
            }
        });

        gameLogic.subscribeStateChanged(new GameStateListener() {
            @Override
            public void changed(GameState state) {
                switch(state) {
                    case ANIMATING:
                        controlsColor = Color.GREEN;
                        break;
                    default:
                        controlsColor = Color.LIGHT_GRAY;
                        break;
                }
            }
        });
    }

    public TextButton getEndTurnButton() {
        return endTurnButton;
    }

    private void drawResourcesHeader() {
        game.batch.begin();
        game.fontSmall.setColor(Color.BLACK);
        game.fontSmall.draw(game.batch, "Unplaced Resources:", 10.0f, (float) TaxeGame.HEIGHT - 250.0f);
        game.fontSmall.draw(game.batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), (float) TaxeGame.WIDTH - 70.0f, 20.0f);
        game.batch.end();
    }

    private void showCurrentPlayerResources() {

        float top = (float) TaxeGame.HEIGHT;
        float x = 10.0f;
        float y = top - 250.0f;
        y -= 50;

        final Player currentPlayer = gameLogic.getPlayerManager().getCurrentPlayer();

        resourceButtons.remove();
        resourceButtons.clear();

        for (final Resource resource : currentPlayer.getResources()) {
            if (resource instanceof Train) {
                Train train = (Train) resource;

                // don't show a button for trains that have been placed
                if (train.getPosition() != null) {
                    continue;
                }

                TrainClicked listener = new TrainClicked(train, skin, mapRenderer, stage);

                TextButton button = new TextButton(resource.toString(), skin);
                button.setPosition(x, y);
                button.addListener(listener);

                resourceButtons.addActor(button);

                y -= 30;
            }
        }

        stage.addActor(resourceButtons);
    }

    private List<String> playerGoalStrings() {
        ArrayList<String> strings = new ArrayList<String>();
        PlayerManager pm = gameLogic.getPlayerManager();
        Player currentPlayer = pm.getCurrentPlayer();

        for (Goal goal : currentPlayer.getGoals()) {
            if(goal.getComplete()) {
                continue;
            }

            strings.add(goal.toString());
        }

        return strings;
    }

    private void showCurrentPlayerGoals() {
        game.batch.begin();
        float top = (float) TaxeGame.HEIGHT;
        game.fontSmall.setColor(Color.BLACK);
        float x = 10.0f;
        float y = top - 10.0f - CONTROLS_HEIGHT;

        String playerGoals = "Current Player (" + gameLogic.getPlayerManager().getCurrentPlayer().toString() + ") Goals:";
        game.fontSmall.draw(game.batch, playerGoals, x, y);

        for (String goalString : playerGoalStrings()) {
            y -= 30;
            game.fontSmall.draw(game.batch, goalString, x, y);
        }

        game.batch.end();
    }

    private void addEndTurnButton() {
        endTurnButton = new TextButton("End Turn", skin);
        endTurnButton.setPosition(TaxeGame.WIDTH - 100.0f, TaxeGame.HEIGHT - 33.0f);
        endTurnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameLogic.getPlayerManager().turnOver();
            }
        });

        gameLogic.subscribeStateChanged(new GameStateListener() {
            @Override
            public void changed(GameState state) {
                if(state == GameState.NORMAL) {
                    endTurnButton.setVisible(true);
                } else {
                    endTurnButton.setVisible(false);
                }
            }
        });

        stage.addActor(endTurnButton);
    }

    // you can read about the debug keys and their functionality in the GitHub wiki
    private void debugKeys() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G) && gameLogic.getState() == GameState.NORMAL) {
            gameLogic.getGoalManager().givePlayerGoal(gameLogic.getPlayerManager().getCurrentPlayer());
        }

//        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
//            gameLogic.getPlayerManager().turnOver();
//        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R) && gameLogic.getState() == GameState.NORMAL) {
            gameLogic.getResourceManager().addRandomResourceToPlayer(gameLogic.getPlayerManager().getCurrentPlayer());
        }
    }

    private void controlsBackground() {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(controlsColor);
        game.shapeRenderer.rect(0, TaxeGame.HEIGHT - CONTROLS_HEIGHT, TaxeGame.WIDTH, CONTROLS_HEIGHT);
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect(0, TaxeGame.HEIGHT - CONTROLS_HEIGHT, TaxeGame.WIDTH, 1);
        game.shapeRenderer.end();
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