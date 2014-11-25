package uk.ac.york.cs.sepr.fvs.taxe;

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
		
		private Position location;
		private Object relation;
		private Texture texture;
		private boolean leftclick = false;
		
		public MapActor(int x, int y, Object relation){
			this.location = new Position(x,y);
			this.relation = relation;
			String image;
			if(this.relation instanceof Station){
				image = "station.png";
			}else{
				image = "missing.png";
			}
			this.texture = new Texture(Gdx.files.internal(image));
			setBounds((float) location.x,(float) location.y,(float) 600,(float) 600);
			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
					((MapActor)event.getTarget()).leftclick = true;
					return true;
				}
			});
		}
		
		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(texture, location.x, location.y);
		}
		
		@Override
		public void act(float delta){
			if(leftclick){
				leftclick = false;
				System.out.println(((Station) this.relation).getName());
			}
		}
	}
	
	private Stage stage = new Stage();
	
	//Map resources
	private Texture mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
	private Image mapImage = new Image(mapTexture);
	
	//Stations
	private List<Station> stations;
	
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
	
	public void renderStations(){
		
	}
	
	public void renderStation(Station station){
		MapActor actor = new MapActor(station.getLocation().x,station.getLocation().y,station);
		actor.setTouchable(Touchable.enabled);
		stage.addActor(actor);
	}
	
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
		Gdx.input.setInputProcessor(stage);
		mapImage.setWidth(TaxeGame.WIDTH);
		mapImage.setHeight(TaxeGame.HEIGHT);
		stage.addActor(mapImage);
		Station testStation = new Station();
		testStation.setName("Test Station");
		testStation.setLocation(new Position(100,100));
		renderStation(testStation);
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
