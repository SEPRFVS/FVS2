package gameLogic.resource;

import Util.Tuple;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;

import java.util.ArrayList;
import java.util.List;

public class Train extends Resource {
    private String image;
    private IPositionable position;
    private Image actor;
    private int speed;

    // Should NOT contain current position!
    private List<Station> route;

    //Station name and turn number
    private List<Tuple<String, Integer>> history;


    public Train(String name, String image, int speed) {
        this.name = name;
        this.image = image;
        this.speed = speed;
        history = new ArrayList<Tuple<String, Integer>>();
        route =  new ArrayList<Station>();
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

    public void setRoute(List<Station> route) {
        this.route = route;
    }

    public List<Station> getRoute(){
        return route;
    }
    
    public int getSpeed() {
        return speed;
    }

    //Station name and turn number
    public List<Tuple<String, Integer>> getHistory() {
        return history;
    }

    //Station name and turn number
    public void addHistory(String stationName, int turn) {
        history.add(new Tuple<String, Integer>(stationName, turn));
    }

    @Override
    public void dispose() {
        if (actor != null) {
            actor.remove();
        }
    }
}
