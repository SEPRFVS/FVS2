package uk.ac.york.cs.sepr.fvs.taxe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Map implements Screen{
	
	//Actor Class
	private class MapActor extends Actor{
		
		private IPositionable location;
		private Object relation;
		private Texture texture;
		private boolean leftclick = false;
		
		//Initialise actor
		public MapActor(int x, int y, Object relation){
			//Configure variables
			this.location = new Position(x,y);
			this.relation = relation;
			//Set image
			String image;
			
			
			if(this.relation instanceof Station){
				image = "station.png";
			}else if(this.relation instanceof Connection){
				image = "connection.png";
			}else{
				image = "missing.png";
			}
			
			
			this.texture = new Texture(Gdx.files.internal(image));
			//Set actor bounds (Not size)
			//setBounds((float) location.x,(float) location.y,(float) 600,(float) 600);
			//Click listener
			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
					((MapActor)event.getTarget()).leftclick = true;
					return true;
				}
			});
		}
		
		//Draw actor on each draw call
		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(texture, location.getX(), location.getY());
		}
		
		//Click handler
		@Override
		public void act(float delta){
			if(leftclick && this.relation instanceof Station){
				leftclick = false;
				System.out.println(((Station) this.relation).getName());
			}
		}
	}
	
	private Stage stage = new Stage(); //Stage - Holds all actors
	
	//Map resources
	private Texture mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
	private Image mapImage = new Image(mapTexture);
	
	//Stations
	private List<Station> stations = new ArrayList<Station>();
	
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
	
	//Display all stations
	public void renderStations(){
		int i = 0;
		for(i = 0;i < stations.size();i++){
			renderStation(stations.get(i));
		}
	}
	
	//Display individual station
	public void renderStation(Station station){
		IPositionable location = station.getLocation();
		MapActor actor = new MapActor(location.getX(), location.getY(), station);
		actor.setTouchable(Touchable.enabled);
		stage.addActor(actor);
	}
	
	//Get Station by Name (May or may not be needed)
	/*public Station getStationByName(String name){
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
		if(found = false){
			return null;
		}
	}*/
	
	//Render Screen on load
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		//Create Map Image
		Gdx.input.setInputProcessor(stage);
		mapImage.setWidth(TaxeGame.WIDTH);
		mapImage.setHeight(TaxeGame.HEIGHT);
		stage.addActor(mapImage);
		
		//Add Stations
		addStation("Test Station", new Position(100,100));
		renderStations();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mapTexture.dispose();
		stage.dispose();
	}

}