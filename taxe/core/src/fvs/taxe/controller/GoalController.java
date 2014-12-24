package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import fvs.taxe.TaxeGame;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalController {
    private List<String> playerGoalStrings() {
        ArrayList<String> strings = new ArrayList<String>();
        PlayerManager pm = gameLogic.getPlayerManager();
        Player currentPlayer = pm.getCurrentPlayer();

        for (Goal goal : currentPlayer.getGoals()) {
            if(goal.getComplete()) {
                continue;
            }

            strings.add(goal.toString());
        }

        return strings;
    }

    private void showCurrentPlayerGoals() {
        game.batch.begin();
        float top = (float) TaxeGame.HEIGHT;
        game.fontSmall.setColor(Color.BLACK);
        float x = 10.0f;
        float y = top - 10.0f - TopBarController.CONTROLS_HEIGHT;

        String playerGoals = "Current Player (" + gameLogic.getPlayerManager().getCurrentPlayer().toString() + ") Goals:";
        game.fontSmall.draw(game.batch, playerGoals, x, y);

        for (String goalString : playerGoalStrings()) {
            y -= 30;
            game.fontSmall.draw(game.batch, goalString, x, y);
        }

        game.batch.end();
    }
}
