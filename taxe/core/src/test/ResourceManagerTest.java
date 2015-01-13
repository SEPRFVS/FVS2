package test;

import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.resource.ResourceManager;
import org.junit.Test;

public class ResourceManagerTest extends LibGdxTest {
    @Test(expected=RuntimeException.class)
    public void testAddResourceToPlayer() throws Exception {
        PlayerManager pm = new PlayerManager();
        Player player = new Player(pm,1);
        ResourceManager rm = new ResourceManager();

        // add enough resources to exceed maximum
        for(int i = 0; i < 20; i++) {
            rm.addRandomResourceToPlayer(player);
        }
    }
}