package fvs.taxe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import gameLogic.map.Connection;
import gameLogic.map.IPositionable;
import gameLogic.map.Position;
import gameLogic.map.Station;

class MapActor extends Actor {

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