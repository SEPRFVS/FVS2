package gameLogic.map;


import Util.Tuple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public final class StationHelper {
    private static ArrayList<Tuple<String, String>> connections;

    private static HashMap<String, Position> stations;
    
    static {
    	JsonReader jsonReader = new JsonReader();
    	JsonValue jsonVal = jsonReader.parse(Gdx.files.local("stations.json"));
    	
        stations = new HashMap<String, Position>();
        for(JsonValue station = jsonVal.getChild("stations"); station != null; station = station.next){
        	String name = "";
        	int x = 0;
        	int y = 0;
        	for(JsonValue val = station.child; val != null; val = val.next){
        		if(val.name.equalsIgnoreCase("name")){
        			name = val.asString();
        		}else if(val.name.equalsIgnoreCase("x")){
        			x = val.asInt();
        		}else{
        			y = val.asInt();
        		}
        	}
        	stations.put(name, new Position(x,y));
        }

        connections = new ArrayList<Tuple<String, String>>();
        for(JsonValue connection = jsonVal.getChild("connections"); connection != null; connection = connection.next){
        	String station1 = "";
        	String station2 = "";
        	for(JsonValue val = connection.child; val != null; val = val.next){
        		if(val.name.equalsIgnoreCase("station1")){
        			station1 = val.asString();
        		}else{
        			station2 = val.asString();
        		}
        	}
        	connections.add(new Tuple<String, String>(station1, station2));
        }
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

    public static boolean doesConnectionExist(String stationName, String anotherStationName) {
        for (Tuple<String, String> connection : connections) {
            if (connection.getFirst().equals(stationName) && connection.getSecond().equals(anotherStationName)
                    || connection.getFirst().equals(anotherStationName) && connection.getSecond().equals(stationName)) {
                return true;
            }
        }

        return false;
    }
}
