package gameLogic.map;


import Util.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public final class StationHelper {
    private static ArrayList<Tuple<String, String>> connections;

    private static HashMap<String, Position> stations;


    static {
        stations = new HashMap<String, Position>();
        stations.put("Madrid", new Position(150, 50));
        stations.put("Paris", new Position(300, 220));
        stations.put("London", new Position(298, 370));
        stations.put("Glasgow", new Position(247, 591));
        stations.put("Lille", new Position(379, 301));
        stations.put("Brussels", new Position(394, 333));
        stations.put("Amsterdam", new Position(401, 380));
        stations.put("Berlin", new Position(560, 401));

        connections = new ArrayList<Tuple<String, String>>();
        connections.add(new Tuple<String, String>("Madrid", "Paris"));
        connections.add(new Tuple<String, String>("London", "Glasgow"));
        connections.add(new Tuple<String, String>("London", "Lille"));
        connections.add(new Tuple<String, String>("Lille", "Paris"));
        connections.add(new Tuple<String, String>("Lille", "Brussels"));
        connections.add(new Tuple<String, String>("Paris", "Brussels"));
        connections.add(new Tuple<String, String>("Brussels", "Amsterdam"));

    }

    public static HashMap<String, Position> getStationData() {
        return stations;
    }

    public static Collection<String> getStationNames() {
        return stations.keySet();
    }

    public static ArrayList<Tuple<String, String>> getConnections(){
        return connections;
    }
}
