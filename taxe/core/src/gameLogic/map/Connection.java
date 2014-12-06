package gameLogic.map;

import gameLogic.map.Station;
import gameLogic.resource.Train;

import java.util.List;

public class Connection {
	private Station station1;
	private Station station2;
	private List<Train> users;
	
	public Connection(Station station1, Station station2){
		this.station1 = station1;
		this.station2 = station2;
	}
	
	public void setStation1(Station station){
		this.station1 = station;
	}
	
	public Station getStation1(){
		return this.station1;
	}
	
	public void setStation2(Station station){
		this.station2 = station;
	}
	
	public Station getStation2(){
		return this.station2;
	}
	
	public List<Train> getUsers(){
		return this.users;
	}
	
	public void addUser(Train train){
		this.users.add(train);
	}
	
	public void removeUser(Train train){
		this.users.remove(train);
	}
}
