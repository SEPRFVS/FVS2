package gameLogic.resource;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gameLogic.map.IPositionable;

import java.util.List;

public class Train extends Resource {
    private String image;
    private IPositionable position;
    private Image actor;
    private int speed;

    // Should NOT contain current position!
    private List<IPositionable> route;


    public Train(String name, String image, int speed) {
        this.name = name;
        this.image = image;
        this.speed = speed;
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

    public void setRoute(List<IPositionable> route) {
        this.route = route;
    }

    public List<IPositionable> getRoute(){
        return route;
    }
    
    public int getSpeed() {
        return speed;
    }
    @Override
    public void dispose() {
        if (actor != null) {
            actor.remove();
        }
    }
}
