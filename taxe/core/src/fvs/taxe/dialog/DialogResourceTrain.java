package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import fvs.taxe.Button;
import gameLogic.Player;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.List;

public class DialogResourceTrain extends Dialog {
    private List<ResourceDialogClickListener> clickListeners = new ArrayList<ResourceDialogClickListener>();

    public DialogResourceTrain(String title, Skin skin) {
        super(title, skin);

        text("What do you want to do with this train?");
        button("Drop", "DROP");
        button("Place at a station", "PLACE");
    }

    private void clicked(Button button) {
        for(ResourceDialogClickListener listener : clickListeners) {
            listener.clicked(button);
        }
    }

    public void subscribeClick(ResourceDialogClickListener listener) {
        clickListeners.add(listener);
    }

    @Override
    protected void result(Object o) {
        if (o == "DROP") {
            clicked(Button.TRAIN_DROP);
        } else if(o == "PLACE") {
            clicked(Button.TRAIN_PLACE);
        }
    }
}
