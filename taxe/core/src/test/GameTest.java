package test;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameTest extends LibGdxTest {
    private PlayerManager pm;

    @Before
    public void setUpGame() throws Exception {
        Game game = Game.getInstance();
        game.getPlayerManager();
        pm = game.getPlayerManager();
    }

    @Test
    public void testInitialisePlayers() {
        Player currentPlayer = pm.getCurrentPlayer();

        // fresh players should start with at least 1 goal and resource
        assertTrue(currentPlayer.getResources().size() > 0);
        assertTrue(currentPlayer.getGoals().size() > 0);
    }

    @Test
    public void testPlayerChanged() throws Exception {
        Player p1 = pm.getCurrentPlayer();
        int resourceCount = p1.getResources().size();
        int goalCount = p1.getGoals().size();

        pm.turnOver();
        pm.turnOver();

        // resource count should increase when p1 has another turn
        assertTrue(p1.getResources().size() > resourceCount);
        assertTrue(p1.getGoals().size() > goalCount);
    }
}