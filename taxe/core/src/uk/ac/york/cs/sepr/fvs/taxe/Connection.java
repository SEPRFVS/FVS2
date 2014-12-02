package uk.ac.york.cs.sepr.fvs.taxe;

import gameLogic.resource.Train;

import java.util.List;

public class Connection {
	private Station station1;
	private Station station2;
	private List<Train> users;
	
	//Initialise
	public Connection(Station station1, Station station2){
		this.station1 = station1;
		this.station2 = station2;
	}
	
	//Station1 Mutators
	public void setStation1(Station station){
		this.station1 = station;
	}
	
	public Station getStation1(){
		return this.station1;
	}
	
	//Station2 Mutators
	public void setSation2(Station station){
		this.station2 = station;
	}
	
	public Station getStation2(){
		return this.station2;
	}
	
	//Users accessor
	public List<Train> getUsers(){
		return this.users;
	}
	
	//Add to Users
	public void addUser(Train train){
		this.users.add(train);
	}
	
	//Remove from Users
	public void removeUser(Train train){
		this.users.remove(train);
	}
}
