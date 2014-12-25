package fvs.taxe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import fvs.taxe.controller.TrainController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.IPositionable;
import gameLogic.resource.Train;

public class TrainActor extends Image {
    public TrainActor(Train train) {
        super(new Texture(Gdx.files.internal(train.getImage())));

        IPositionable position = train.getPosition();

        train.setActor(this);
        setSize(30f, 30f);
        setPosition(position.getX() - TrainController.TRAIN_OFFSET, position.getY() - TrainController.TRAIN_OFFSET);
    }

    @Override
    public void act (float delta) {
        if (Game.getInstance().getState() == GameState.ANIMATING) {
            super.act(delta);
        }
    }
}
