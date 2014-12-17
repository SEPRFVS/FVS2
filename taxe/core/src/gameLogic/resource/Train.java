package gameLogic.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gameLogic.map.IPositionable;

public class Train extends Resource {
    private String image;
    private IPositionable position;
    private Image actor;

    public Train(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return "trains/" + image;
    }

    public String getCursorImage() {
        return "trains/cursor/" + image;
    }

    public void setPosition(IPositionable position) {
        this.position = position;
    }

    public IPositionable getPosition() {
        return position;
    }

    public void setActor(Image actor) {
        this.actor = actor;
    }

    public Image getActor(){
        return actor;
    }
}
