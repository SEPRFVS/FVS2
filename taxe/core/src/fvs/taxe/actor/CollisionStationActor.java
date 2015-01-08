package fvs.taxe.actor;

import gameLogic.map.IPositionable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CollisionStationActor extends Image {
	private int width = 35;
    private int height = 22;

    public CollisionStationActor(IPositionable location) {
        super(new Texture(Gdx.files.internal("station_icon.png")));

        setSize(width, height);
        setPosition(location.getX() - width / 2, location.getY() - height / 2);
    }
}
