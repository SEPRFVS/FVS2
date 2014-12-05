package uk.ac.york.cs.sepr.fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class Map extends ScreenAdapter {
	
	//Actor Class
	private class MapActor extends Actor{
		
		private IPositionable location;
		private Object relation;
		private Texture texture;
		private boolean leftclick = false;
		private Size size;
		
		//Initialise actor
		public MapActor(int x, int y, Object relation){
			//Configure variables
			this.location = new Position(x,y);
			this.relation = relation;
			//Set image, Size and Name
			String image;
			size = new Size(32,32);
			
			if(this.relation instanceof Station){
				image = "station.png";
				this.setName(((Station) this.relation).getName());
			}else if(this.relation instanceof Connection){
				image = "connection.png";
				this.setName(((Connection) this.relation).getStation1().getName() + "-" + ((Connection) this.relation).getStation2().getName());
				this.location = new Position(x+16,y+16);
			}else{
				image = "missing.png";
			}
			
			
			this.texture = new Texture(Gdx.files.internal(image));
			//Set actor bounds (Not size)
			setBounds((float) location.getX(),(float) location.getY(),(float) size.getHeight(),(float) size.getWidth());
			//Click listener
			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
					((MapActor)event.getTarget()).leftclick = true;
					return true;
				}
			});
		}
		
		//Expand connection to correct size
		public void expandConnection(IPositionable lowerCorner, IPositionable upperCorner){
			//Set sprite size
			Size size = new Size(upperCorner.getX() - lowerCorner.getX(), upperCorner.getY() - lowerCorner.getY());
			this.size = size;
			//Rotate rails using SOHCAHTOA
			double rotation = Math.toDegrees(Math.atan(size.getWidth()/size.getHeight()));
			this.setRotation((float) rotation); //TODO Doesn't actually rotate
		}
		
		//Draw actor on each draw call
		@Override
		public void draw(Batch batch, float alpha){
			batch.draw(texture, location.getX(), location.getY(),size.getHeight(),size.getWidth());
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

	final private TaxeGame game;
	private OrthographicCamera camera;
	private Stage stage; // Stage - Holds all actors
	private Group actors; //Group - Hold all actors to allow searching and redrawing
	private Vector3 touchPoint;
	private Texture mapTexture;
	private Image mapImage;
	private List<Station> stations;
	private List<Connection> connections;

	public Map(TaxeGame game){
		this.game = game;

		camera = new OrthographicCamera(TaxeGame.WIDTH, TaxeGame.HEIGHT);
		camera.setToOrtho(false); // Makes the origin to be in the lower left corner
		stage = new Stage();
		actors = stage.getRoot();
		touchPoint = new Vector3();
		mapTexture = new Texture(Gdx.files.internal("gamemap.png"));
		mapImage = new Image(mapTexture);
		stations = new ArrayList<Station>();
		connections = new ArrayList<Connection>();

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
	
	//Display all stations
	public void renderStations(){
		for (Station station : stations) {
			renderStation(station);
		}
	}
	
	//Display individual station
	public void renderStation(Station station){
		IPositionable location = station.getLocation();
		MapActor actor = new MapActor(location.getX(), location.getY(), station);
		actor.setTouchable(Touchable.enabled);
		actors.addActor(actor);
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
	
	//Display all connections
	public void renderConnections(){
		for(Connection connection : connections){
			renderConnection(connection);
		}
	}
	
	//Display Individual connection
	public void renderConnection(Connection connection){
		IPositionable lowerCorner, upperCorner;
		if(connection.getStation1().getLocation().getX() < connection.getStation2().getLocation().getY()){
			//Lower position at X
			lowerCorner = connection.getStation1().getLocation();
			upperCorner = connection.getStation2().getLocation();
		}else{
			//Lower position at Y
			lowerCorner = connection.getStation2().getLocation();
			upperCorner = connection.getStation1().getLocation();
		}
		MapActor actor = new MapActor(lowerCorner.getX(), lowerCorner.getY(), connection);
		actor.expandConnection(lowerCorner, upperCorner);
		actors.addActor(actor);
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
	
	//Render Screen on load
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	// Called when Map becomes current screen of the game
	public void show() {
		//Create Map Image
		Gdx.input.setInputProcessor(stage);
		mapImage.setWidth(TaxeGame.WIDTH);
		mapImage.setHeight(TaxeGame.HEIGHT);
		stage.addActor(mapImage);
		
		//Add Stations - TODO Make a useful implementation
		addStation("Madrid", new Position(150, 50));
		addStation("Paris", new Position(300,220));
		renderStations();
		
		//Add Connections - TODO Make a useful implementation
		addConnection("Paris","Madrid");
		renderConnections();
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mapTexture.dispose();
		stage.dispose();
	}

}