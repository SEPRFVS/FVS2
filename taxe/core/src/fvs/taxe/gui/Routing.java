package fvs.taxe.gui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.MapRenderer;
import fvs.taxe.RouteConfirmedListener;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.IPositionable;

import java.util.List;

public class Routing {
    private Group routingButtons = new Group();
    private Stage stage;
    private Skin skin;
    private MapRenderer mapRenderer;

    public Routing(MapRenderer mapRenderer, RouteConfirmedListener listener,
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
}
