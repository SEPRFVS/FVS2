package fvs.taxe.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.map.IPositionable;
import gameLogic.map.Position;
import gameLogic.resource.Train;

public class TrainActor extends Image {
    public static int width = 36;
    public static int height = 36;
    public Train train;

    Rectangle bounds;

    public TrainActor(Train train) {
        super(new Texture(Gdx.files.internal(train.getImage())));

        IPositionable position = train.getPosition();

        train.setActor(this);
        this.train = train;
        setSize(width, height);
        bounds = new Rectangle();
        setPosition(position.getX() - width / 2, position.getY() - height / 2);
    }

    @Override
    public void act (float delta) {
        if (Game.getInstance().getState() == GameState.ANIMATING) {
            super.act(delta);
            updateBounds();
            train.setPosition(new Position((int) this.getX(), (int) this.getY()));
        }
    }

    private void updateBounds() {
        bounds.set(getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
