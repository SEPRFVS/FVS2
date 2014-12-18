package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.MapRenderer;
import gameLogic.Player;
import gameLogic.resource.Train;

public class TrainClicked extends ClickListener {
    private Train train;
    private Player currentPlayer;
    private Skin skin;
    private MapRenderer mapRenderer;
    private Stage stage;

    public TrainClicked(Train t, Player p, Skin skin, MapRenderer mr, Stage stage) {
        train = t;
        currentPlayer = p;
        mapRenderer = mr;
        this.skin = skin;
        this.stage = stage;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        DialogButtonClicked listener = new DialogButtonClicked(currentPlayer, train, mapRenderer);
        DialogResourceTrain dia = new DialogResourceTrain(train.toString(), skin, train.getPosition() != null);
        dia.show(stage);
        dia.subscribeClick(listener);
    }

}
