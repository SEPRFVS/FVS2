package test;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    private PlayerManager pm;

    @Before
    public void setUp() throws Exception {
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
}