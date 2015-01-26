package test;

import gameLogic.Player;
import gameLogic.PlayerManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayerManagerTest {
    private PlayerManager pm;

    @Before
    public void setUp() throws Exception {
        pm = new PlayerManager();
        pm.createPlayers(2);
    }

    @Test
    public void testGetCurrentPlayer() throws Exception {
        Player p1 = pm.getCurrentPlayer();
        pm.turnOver();

        // player should change after PlayerManager.turnOver() is called
        assertFalse(p1.equals(pm.getCurrentPlayer()));
    }

    @Test
    public void testTurnNumber() throws  Exception {
        int previous = pm.getTurnNumber();
        pm.turnOver();

        assertTrue("Turn number did not change", previous < pm.getTurnNumber());
    }


}