package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import gameLogic.Player;
import gameLogic.resource.Train;

public class DialogResourceTrain extends Dialog {
    private Player player;
    private Train train;

    public DialogResourceTrain(String title, Skin skin) {
        super(title, skin);

        text("What do you want to do with this train?");
        button("Drop", "DROP");
        button("Place at a station", "PLACE");
    }

    public void dropTrainArgs(Player p, Train t) {
        player = p;
        train = t;
    }

    @Override
    protected void result(Object o) {
        if (o == "DROP") {
            player.removeResource(train);
        } else if(o == "PLACE") {

        }
    }
}
