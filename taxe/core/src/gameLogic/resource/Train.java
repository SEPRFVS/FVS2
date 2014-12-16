package gameLogic.resource;

import gameLogic.map.IPositionable;

public class Train extends Resource {
    private String image;
    private IPositionable position;

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
}
