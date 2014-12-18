package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import fvs.taxe.Button;
import fvs.taxe.MapRenderer;
import fvs.taxe.StationClickListener;
import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class DialogButtonClicked implements ResourceDialogClickListener {
    private Player currentPlayer;
    private Train train;
    private MapRenderer mapRenderer;

    public DialogButtonClicked(Player p, Train t, MapRenderer mr) {
        currentPlayer = p;
        train = t;
        mapRenderer = mr;
    }

    @Override
    public void clicked(Button button) {
        switch (button) {
            case TRAIN_DROP:
                currentPlayer.removeResource(train);
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
                        Image trainImage = mapRenderer.renderTrain(train, currentPlayer);
                        train.setActor(trainImage);

                        // java.util.ConcurrentModificationException
                        mapRenderer.unsubscribeStationClick(this);
                    }
                });

                break;
        }
    }
}
