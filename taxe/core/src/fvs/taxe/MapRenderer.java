package fvs.taxe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.actor.StationActor;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.dialog.TrainClicked;
import gameLogic.Game;
import gameLogic.map.*;
import gameLogic.resource.Train;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;


public class MapRenderer {
    public static final int ANIMATION_TIME = 2;

    private Stage stage;
    private TaxeGame game;
    private Map map;
    private Skin skin;

    private Tooltip tooltip;


    public MapRenderer(TaxeGame game, Stage stage, Skin skin, Map map) {
        this.game = game;
        this.stage = stage;
        this.skin = skin;
        this.map = map;

        tooltip = new Tooltip(skin);
        stage.addActor(tooltip);
    }

    public Map getMap() {
        return map;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }
















}
