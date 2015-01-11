package gameLogic.map;

import Util.Tuple;

import java.util.*;

public class Map {
    private List<Station> stations;
    private List<Connection> connections;
    private Random random = new Random();

    public Map() {
        stations = new ArrayList<Station>();
        connections = new ArrayList<Connection>();

        addStations();
        addConnections();
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

    public void addStations() {
        Collection<String> stationNames = StationHelper.getStationNames();
        HashMap<String, Tuple<Position,Boolean>> stationData = StationHelper.getStationData();

        for (String name : stationNames) {
        	if(stationData.get(name).getSecond()) {
        		addJunction(name, stationData.get(name).getFirst());
        	} else {
        		addStation(name, stationData.get(name).getFirst());
        	}
        }
    }

    public List<Station> getStations() {
        return stations;
    }

    //Get Connections
    public List<Connection> getConnections() {
        return connections;
    }

    //Add Connection
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

    public void addConnections() {
        ArrayList<Tuple<String, String>> connectionPairs = StationHelper.getConnections();
        for (Tuple<String, String> connectionPair : connectionPairs) {
            addConnection(connectionPair.getFirst(), connectionPair.getSecond());
        }
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

    //Get Station by Name (May or may not be needed)
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
}
