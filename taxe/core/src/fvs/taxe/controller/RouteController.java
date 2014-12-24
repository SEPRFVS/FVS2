package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.MapRenderer;
import fvs.taxe.RouteConfirmedListener;
import fvs.taxe.TaxeGame;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.IPositionable;

import java.util.List;

public class RouteController {
    private Group routingButtons = new Group();
    private Stage stage;
    private Skin skin;

    public RouteController(MapRenderer mapRenderer, RouteConfirmedListener listener,
                           List<IPositionable> initialPosition) {
        this.stage = mapRenderer.getStage();
        this.skin = mapRenderer.getSkin();
        this.mapRenderer = mapRenderer;

        mapRenderer.setPlacingPositions(initialPosition);
        Game.getInstance().setState(GameState.ROUTING);
        addRoutingButtons(listener);
    }

    private void addRoutingButtons(final RouteConfirmedListener listener) {
        TextButton doneRouting = new TextButton("Route Complete", skin);
        TextButton cancel = new TextButton("Cancel", skin);

        doneRouting.setPosition(TaxeGame.WIDTH - 250, TaxeGame.HEIGHT - 33);
        cancel.setPosition(TaxeGame.WIDTH - 100, TaxeGame.HEIGHT - 33);

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
}
