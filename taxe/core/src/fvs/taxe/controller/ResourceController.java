package fvs.taxe.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import fvs.taxe.TaxeGame;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Player;
import gameLogic.PlayerChangedListener;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

public class ResourceController {
    private Context context;
    private Group resourceButtons = new Group();

    public ResourceController(final Context context) {
        this.context = context;

        context.getGameLogic().getPlayerManager().subscribePlayerChanged(new PlayerChangedListener() {
            @Override
            public void changed() {
                drawPlayerResources(context.getGameLogic().getPlayerManager().getCurrentPlayer());
            }
        });
    }

    public void drawHeaderText() {
        TaxeGame game = context.getTaxeGame();

        game.batch.begin();
        game.fontSmall.setColor(Color.BLACK);
        game.fontSmall.draw(game.batch, "Unplaced Resources:", 10.0f, (float) TaxeGame.HEIGHT - 250.0f);
        game.batch.end();
    }

    public void drawPlayerResources(Player player) {

        float top = (float) TaxeGame.HEIGHT;
        float x = 10.0f;
        float y = top - 250.0f;
        y -= 50;

        resourceButtons.remove();
        resourceButtons.clear();

        for (final Resource resource : player.getResources()) {
            if (resource instanceof Train) {
                Train train = (Train) resource;

                // don't show a button for trains that have been placed
                if (train.getPosition() != null) {
                    continue;
                }

                TrainClicked listener = new TrainClicked(context, train);

                TextButton button = new TextButton(resource.toString(), context.getSkin());
                button.setPosition(x, y);
                button.addListener(listener);

                resourceButtons.addActor(button);

                y -= 30;
            }
        }

        context.getStage().addActor(resourceButtons);
    }
}
