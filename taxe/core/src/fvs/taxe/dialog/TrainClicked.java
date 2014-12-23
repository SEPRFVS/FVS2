package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.MapRenderer;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Train;

public class TrainClicked extends ClickListener {
    private Train train;
    private Skin skin;
    private MapRenderer mapRenderer;
    private Stage stage;

    public TrainClicked(Train train, Skin skin, MapRenderer mr, Stage stage) {
        this.train = train;
        mapRenderer = mr;
        this.skin = skin;
        this.stage = stage;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (Game.getInstance().getState() != GameState.NORMAL) return;

        // current player can't be passed in as it changes so find out current player at this instant
        Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

        if (!train.isOwnedBy(currentPlayer)) {
            return;
        }

        DialogButtonClicked listener = new DialogButtonClicked(currentPlayer, train, mapRenderer);
        DialogResourceTrain dia = new DialogResourceTrain(train.toString(), skin, train.getPosition() != null);
        dia.show(stage);
        dia.subscribeClick(listener);
    }

}
