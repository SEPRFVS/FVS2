package fvs.taxe.controller;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.TaxeGame;
import gameLogic.Game;

public class Context {
    private TaxeGame taxeGame;
    private Stage stage;
    private Skin skin;
    private Game gameLogic;
    private RouteController routeController;

    public Context(Stage stage, Skin skin, TaxeGame taxeGame) {
        this.stage = stage;
        this.skin = skin;
        this.taxeGame = taxeGame;
        this.gameLogic = Game.getInstance();
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public TaxeGame getTaxeGame() {
        return taxeGame;
    }

    public Game getGameLogic() {
        return gameLogic;
    }

    public RouteController getRouteController() {
        return routeController;
    }

    public void setRouteController(RouteController routeController) {
        this.routeController = routeController;
    }
}
