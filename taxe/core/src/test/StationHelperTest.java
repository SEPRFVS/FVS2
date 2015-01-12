package test;

import gameLogic.map.Map;
import org.junit.Test;

import static org.junit.Assert.*;

public class StationHelperTest {

    @Test
    public void testDoesConnectionExist() throws Exception {
        Map map = new Map();

        assertTrue(map.doesConnectionExist("Madrid", "Paris"));
        assertFalse(map.doesConnectionExist("London", "Paris"));
    }
}