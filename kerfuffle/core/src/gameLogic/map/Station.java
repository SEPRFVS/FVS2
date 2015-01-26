package gameLogic.map;

import fvs.taxe.actor.StationActor;

public class Station{
	private String name;
	private IPositionable location;
	private StationActor actor;

	public Station(String name, IPositionable location) {
		this.name = name;
		this.location = location;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public IPositionable getLocation() {
		return location;
	}
	public void setLocation(IPositionable location) {
		this.location = location;
	}
	
	public void setActor(StationActor actor){
		this.actor = actor;
	}
	
	public StationActor getActor(){
		return actor;
	}
	
}
