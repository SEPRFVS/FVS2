package test;


import gameLogic.map.Position;
import gameLogic.map.Station;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StationTest {
    @Test
    public void StationsTest() throws Exception {
        int x = 5000;
        int y = 7000;
        String name = "TestStation";

        Station testStation = new Station(name, new Position(x, y));

        assertTrue("Position is wrong", testStation.getLocation().getX() == x && testStation.getLocation().getY() == y);
        assertTrue("Name is wrong", testStation.getName().equals(name));
    }
}
