package gameLogic.map;

import java.util.ArrayList;
import java.util.List;

public class Map {
    private List<Station> stations;
    private List<Connection> connections;

    public Map() {
        stations = new ArrayList<Station>();
        connections = new ArrayList<Connection>();

        //Add Stations - TODO Make a useful implementation
        addStation("Madrid", new Position(150, 50));
        addStation("Paris", new Position(300, 220));

        //Add Connections - TODO Make a useful implementation
        addConnection("Paris", "Madrid");
    }

    public Station addStation(String name, Position location){
        Station newStation = new Station();
        newStation.setName(name);
        newStation.setLocation(location);
        stations.add(newStation);
        return newStation;
    }

    public List<Station> getStations(){
        return stations;
    }

    //Get Connections
    public List<Connection> getConnections(){
        return connections;
    }

    //Add Connection
    public Connection addConnection(Station station1, Station station2){
        Connection newConnection = new Connection(station1, station2);
        connections.add(newConnection);
        return newConnection;
    }

    //Add Connection by Names
    public Connection addConnection(String station1, String station2){
        Station st1 = getStationByName(station1);
        Station st2 = getStationByName(station2);
        return addConnection(st1, st2);
    }

    //Get connections from station
    @SuppressWarnings("null")
    public List<Connection> getConnectionsFromStation(Station station){
        List<Connection> results = null;
        for(Connection connection : connections){
            if(connection.getStation1() == station || connection.getStation2() == station){
                results.add(connection);
            }
        }
        return results;
    }

    //Get Station by Name (May or may not be needed)
    public Station getStationByName(String name){
        int i = 0;
        boolean found = false;
        while(i < stations.size() && found == false){
            if(stations.get(i).getName() == name){
                found = true;
                return stations.get(i);
            }else{
                i++;
            }
        }
        return null;
    }
}
