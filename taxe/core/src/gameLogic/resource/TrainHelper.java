package gameLogic.resource;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import Util.Tuple;

public final class TrainHelper {
	private static ArrayList<Tuple<String, Integer>> trains;
	
	static {
		JsonReader jsonReader = new JsonReader();
    	JsonValue jsonVal = jsonReader.parse(Gdx.files.local("trains.json"));
    	
    	trains = new ArrayList<Tuple<String, Integer>>();
    	for(JsonValue train = jsonVal.getChild("trains"); train != null; train = train.next()){
    		String name = "";
    		int speed = 50;
    		for(JsonValue val  = train.child; val != null; val = val.next()){
    			if(val.name.equalsIgnoreCase("name")){
    				name = val.asString();
    			}else{
    				speed = val.asInt();
    			}
    		}
    		trains.add(new Tuple<String, Integer>(name, speed));
    	}
	}
	
	public static ArrayList<String> getTrainNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(Tuple<String,Integer> train : trains){
			names.add(train.getFirst());
		}
		return names;
	}
	
	public static ArrayList<Tuple<String, Integer>> getTrains(){
		return trains;
	}
}
