package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import gameLogic.map.Connection;
import gameLogic.map.IPositionable;
import gameLogic.map.Position;
import gameLogic.map.Station;

class MapActor extends GeneralActor {

    //Initialise actor
    public MapActor(int x, int y, Object relation){
    	super(x,y,relation);
        
        //Set image, Size and Name
        String image;

        if(this.relation instanceof Station){
            image = "station.png";
            this.setName(((Station) this.relation).getName());
        }else if(this.relation instanceof Connection){
            image = "connection.png";
            this.setName(((Connection) this.relation).getStation1().getName() + "-" + ((Connection) this.relation).getStation2().getName());
            this.location = new Position(x+8,y+8); //TODO what is this hardcoded 16??
        }else{
            image = "missing.png";
        }
        this.texture = new Texture(Gdx.files.internal(image));
    }

    //Expand connection to correct size
    public void expandConnection(IPositionable lowerCorner, IPositionable upperCorner){
        //Set sprite size
        setSize(upperCorner.getX() - lowerCorner.getX(), upperCorner.getY() - lowerCorner.getY());
        //Rotate rails using SOHCAHTOA
        double rotation = Math.toDegrees(Math.atan(getSize().getWidth()/getSize().getHeight()));
        this.rotateBy((float) -rotation);
    }

    //Click handler
    @Override
    public void act(float delta){
        if(leftclick && this.relation instanceof Station){
            leftclick = false;
            System.out.println(((Station) this.relation).getName());
        }else if(leftclick && this.relation instanceof Connection){
        	leftclick = false;
        	System.out.println(((Connection) this.relation).getStation1().getName() + " - " + ((Connection) this.relation).getStation2().getName());
        }
    }
}