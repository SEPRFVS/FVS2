package gameLogic.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private List<Station> stations;
    private List<Connection> connections;
    private Random random = new Random();

    public Map() {
        stations = new ArrayList<Station>();
        connections = new ArrayList<Connection>();

        initialise();
    }

    private void initialise() {
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonVal = jsonReader.parse(Gdx.files.local("stations.json"));

        parseStations(jsonVal);
        parseConnections(jsonVal);
    }

    private void parseConnections(JsonValue jsonVal) {
        for(JsonValue connection = jsonVal.getChild("connections"); connection != null; connection = connection.next) {
            String station1 = "";
            String station2 = "";

            for(JsonValue val = connection.child; val != null; val = val.next) {
                if(val.name.equalsIgnoreCase("station1")) {
                    station1 = val.asString();
                } else {
                    station2 = val.asString();
                }
            }

            addConnection(station1, station2);
        }
    }

    private void parseStations(JsonValue jsonVal) {
        for(JsonValue station = jsonVal.getChild("stations"); station != null; station = station.next) {
            String name = "";
            int x = 0;
            int y = 0;
            int xoffset = 255;
            boolean isJunction = false;

            for(JsonValue val = station.child; val != null; val = val.next) {
                if(val.name.equalsIgnoreCase("name")) {
                    name = val.asString();
                } else if(val.name.equalsIgnoreCase("x")) {
                    x = xoffset+ val.asInt();
                } else if(val.name.equalsIgnoreCase("y")) {
                    y = val.asInt();
                } else {
                    isJunction = val.asBoolean();
                }
            }

            if (isJunction) {
                addJunction(name, new Position(x,y));
            } else {
                addStation(name, new Position(x, y));
            }
        }
    }

    public boolean doesConnectionExist(String stationName, String anotherStationName) {
        for (Connection connection : connections) {
            String s1 = connection.getStation1().getName();
            String s2 = connection.getStation2().getName();

            if (s1.equals(stationName) && s2.equals(anotherStationName)
                || s1.equals(anotherStationName) && s2.equals(stationName)) {
                return true;
            }
        }

        return false;
    }

    public Station getRandomStation() {
        return stations.get(random.nextInt(stations.size()));
    }

    public Station addStation(String name, Position location) {
        Station newStation = new Station(name, location);
        stations.add(newStation);
        return newStation;
    }
    
    public CollisionStation addJunction(String name, Position location) {
    	CollisionStation newJunction = new CollisionStation(name, location);
    	stations.add(newJunction);
    	return newJunction;
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Connection addConnection(Station station1, Station station2) {
        Connection newConnection = new Connection(station1, station2);
        connections.add(newConnection);
        return newConnection;
    }

    //Add Connection by Names
    public Connection addConnection(String station1, String station2) {
        Station st1 = getStationByName(station1);
        Station st2 = getStationByName(station2);
        return addConnection(st1, st2);
    }

    //Get connections from station
    public List<Connection> getConnectionsFromStation(Station station) {
        List<Connection> results = new ArrayList<Connection>();
        for(Connection connection : connections) {
            if(connection.getStation1() == station || connection.getStation2() == station) {
                results.add(connection);
            }
        }
        return results;
    }

    public Station getStationByName(String name) {
        int i = 0;
        while(i < stations.size()) {
            if(stations.get(i).getName().equals(name)) {
                return stations.get(i);
            } else{
                i++;
            }
        }
        return null;
    }

    public Station getStationFromPosition(IPositionable position) {
        for (Station station : stations) {
            if (station.getLocation().equals(position)) {
                return station;
            }
        }

        throw new RuntimeException("Station does not exist for that position");
    }

    public List<Station> createRoute(List<IPositionable> positions) {
        List<Station> route = new ArrayList<Station>();

        for (IPositionable position : positions) {
            route.add(getStationFromPosition(position));
        }

        return route;
    }
}
