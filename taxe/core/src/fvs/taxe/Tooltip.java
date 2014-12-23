package fvs.taxe;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class Tooltip extends Window {
    public Tooltip(Skin skin) {
        super("", skin);

        setSize(150, 20);
        setVisible(false);
    }

    public void show(String content) {
        setTitle(content);
        setVisible(true);
        toFront();
    }

    public void hide() {
        setVisible(false);
    }
}
