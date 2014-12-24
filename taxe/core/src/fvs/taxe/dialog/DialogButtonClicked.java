package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import fvs.taxe.Button;
import fvs.taxe.MapRenderer;
import fvs.taxe.RouteListener;
import fvs.taxe.StationClickListener;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class DialogButtonClicked implements ResourceDialogClickListener {
    private Player currentPlayer;
    private Train train;
    private MapRenderer mapRenderer;

    public DialogButtonClicked(Player player, Train train, MapRenderer mr) {
        currentPlayer = player;
        this.train = train;
        mapRenderer = mr;
    }

    @Override
    public void clicked(Button button) {
        switch (button) {
            case TRAIN_DROP:
                currentPlayer.removeResource(train);
                break;
            case TRAIN_PLACE:
                Pixmap pixmap = new Pixmap(Gdx.files.internal(train.getCursorImage()));
                Gdx.input.setCursorImage(pixmap, 10, 25); // these numbers will need tweaking
                pixmap.dispose();

                Game.getInstance().setState(GameState.PLACING);

                mapRenderer.subscribeStationClick(new StationClickListener() {
                    @Override
                    public void clicked(Station station) {
                        train.setPosition(station.getLocation());
                        train.addHistory(station.getName(), Game.getInstance().getPlayerManager().getTurnNumber());

                        Gdx.input.setCursorImage(null, 0, 0);
                        Image trainImage = mapRenderer.renderTrain(train);
                        train.setActor(trainImage);

                        mapRenderer.unsubscribeStationClick(this);
                        Game.getInstance().setState(GameState.NORMAL);
                    }
                });

                break;
            case TRAIN_ROUTE:
                RouteListener routeListener = new RouteListener(mapRenderer, train);
                mapRenderer.subscribeStationClick(routeListener);

                break;
        }
    }
}
