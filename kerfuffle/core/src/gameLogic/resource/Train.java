package gameLogic.resource;

import Util.Tuple;
import fvs.taxe.actor.TrainActor;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;

import java.util.ArrayList;
import java.util.List;

public class Train extends Resource {
    private String leftImage;
    private String rightImage;
    private IPositionable position;
    private TrainActor actor;
    private int speed;
    // Final destination should be set to null after firing the arrival event
    private Station finalDestination;

    // Should NOT contain current position!
    private List<Station> route;

    //Station name and turn number
    private List<Tuple<String, Integer>> history;


    public Train(String name, String leftImage, String rightImage, int speed) {
        this.name = name;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.speed = speed;
        history = new ArrayList<Tuple<String, Integer>>();
        route =  new ArrayList<Station>();
    }
    
    public String getName() {
    	return name;
    }

    public String getLeftImage() {
        return "trains/" + leftImage;
    }

    public String getRightImage() {
        return "trains/" + rightImage;
    }

    public String getCursorImage() {
        return "trains/cursor/" + leftImage;
    }

    public void setPosition(IPositionable position) {
        this.position = position;
        changed();
    }

    public IPositionable getPosition() {
        return position;
    }

    public void setActor(TrainActor actor) {
        this.actor = actor;
    }

    public TrainActor getActor() {
        return actor;
    }

    public void setRoute(List<Station> route) {
        // Final destination should be set to null after firing the arrival event
        if (route != null && route.size() > 0) finalDestination = route.get(route.size() - 1);

        this.route = route;
    }

    public boolean isMoving() {
        return finalDestination != null;
    }

    public List<Station> getRoute() {
        return route;
    }

    public Station getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(Station station) {
        finalDestination = station;
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
