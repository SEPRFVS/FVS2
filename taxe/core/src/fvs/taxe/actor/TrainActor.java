package fvs.taxe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import fvs.taxe.MapRenderer;
import gameLogic.map.IPositionable;
import gameLogic.resource.Train;

public class TrainActor extends Image {
    public TrainActor(Train train) {
        super(new Texture(Gdx.files.internal(train.getImage())));

        IPositionable position = train.getPosition();

        train.setActor(this);
        setSize(30f, 30f);
        setPosition(position.getX() - MapRenderer.TRAIN_OFFSET, position.getY() - MapRenderer.TRAIN_OFFSET);
    }
}
