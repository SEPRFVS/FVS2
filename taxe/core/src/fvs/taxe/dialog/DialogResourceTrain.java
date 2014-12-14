package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DialogResourceTrain extends Dialog {
    public DialogResourceTrain(String title, Skin skin) {
        super(title, skin);

        text("What do you want to do with this train?");
        button("Drop", "DROP");
        button("Place at a station", "PLACE");
    }

    @Override
    protected void result(Object o) {
        if (o == "DROP") {

        } else if(o == "PLACE") {

        }
    }
}
