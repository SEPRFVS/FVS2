package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.dialog.DialogResourceTrain;
import fvs.taxe.dialog.ResourceDialogClickListener;
import gameLogic.*;
import gameLogic.goal.Goal;
import gameLogic.map.Station;
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

        mapRenderer = new MapRenderer(game, stage);

        gameLogic.getPlayerManager().subscribeTurnChanged(new TurnListener() {
            @Override
            public void changed() {
                animateTrainMovements();
            }
        });
    }

    private void animateTrainMovements(){
        for (Player player : gameLogic.getPlayerManager().getAllPlayers()){
            for (Resource train : player.getResources()){
                mapRenderer.moveTrainByTurn((Train) train);
            }
        }

    }

    private void drawResourcesHeader() {
        game.batch.begin();
        game.fontSmall.setColor(Color.BLACK);
        game.fontSmall.draw(game.batch, "Resources:", 10.0f, (float) TaxeGame.HEIGHT - 250.0f);
        game.fontSmall.draw(game.batch, "FPS:" + Gdx.graphics.getFramesPerSecond(), (float) TaxeGame.WIDTH - 100.0f, (float) TaxeGame.HEIGHT - 10.0f);
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

        for (final Resource r : currentPlayer.getResources()) {
            final Train train = (Train)r;

            TextButton button = new TextButton(r.toString(), skin);
            button.setPosition(x, y);
            button.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    DialogResourceTrain dia = new DialogResourceTrain(r.toString(), skin, train.getPosition() != null);
                    dia.show(stage);
                    dia.subscribeClick(new ResourceDialogClickListener() {
                        @Override
                        public void clicked(Button button) {
                            switch(button) {
                                case TRAIN_DROP:
                                    currentPlayer.removeResource(r);
                                    break;
                                case TRAIN_PLACE:
                                    Pixmap pm = new Pixmap(Gdx.files.internal(train.getCursorImage()));
                                    Gdx.input.setCursorImage(pm, 10, 25); // these numbers will need tweaking
                                    pm.dispose();

                                    mapRenderer.subscribeStationClick(new StationClickListener() {
                                        @Override
                                        public void clicked(Station station) {
                                            train.setPosition(station.getLocation());

                                            Gdx.input.setCursorImage(null, 0, 0);
                                            Image trainImage = mapRenderer.renderTrain(train);
                                            train.setActor(trainImage);

                                            // java.util.ConcurrentModificationException
                                            mapRenderer.unsubscribeStationClick(this);
                                        }
                                    });

                                    break;
                            }
                        }
                    });
                }
            });

            resourceButtons.addActor(button);

            y -= 30;
        }

        stage.addActor(resourceButtons);
    }

    private List<String> playerGoalStrings() {
        ArrayList<String> strings = new ArrayList<String>();
        PlayerManager pm = gameLogic.getPlayerManager();
        Player currentPlayer = pm.getCurrentPlayer();

        for (Goal g : currentPlayer.getGoals()) {
            strings.add(g.toString());
        }

        return strings;
    }

    private void showCurrentPlayerGoals() {
        game.batch.begin();
        float top = (float) TaxeGame.HEIGHT;
        game.fontSmall.setColor(Color.BLACK);
        float x = 10.0f;
        float y = top - 10.0f;

        String playerGoals = "Current Player (" + gameLogic.getPlayerManager().getCurrentPlayer().toString() + ") Goals:";
        game.fontSmall.draw(game.batch, playerGoals, x, y);

        for (String s : playerGoalStrings()) {
            y -= 30;
            game.fontSmall.draw(game.batch, s, x, y);
        }

        game.batch.end();
    }

    // you can read about the debug keys and their functionality in the GitHub wiki
    private void debugKeys() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            gameLogic.getGoalManager().givePlayerGoal(gameLogic.getPlayerManager().getCurrentPlayer());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            gameLogic.getPlayerManager().turnOver();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            gameLogic.getResourceManager().addRandomResourceToPlayer(gameLogic.getPlayerManager().getCurrentPlayer());
        }


    }

    // called every frame
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(mapTexture, 0, 0);
        game.batch.end();

        mapRenderer.renderConnections();

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
    }


    @Override
    public void dispose() {
        // TODO Auto-generated method stub
        mapTexture.dispose();
        stage.dispose();
    }

}