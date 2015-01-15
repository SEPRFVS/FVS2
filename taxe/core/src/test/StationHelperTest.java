package test;

import gameLogic.map.Map;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StationHelperTest extends LibGdxTest {
    @Test
    public void testDoesConnectionExist() throws Exception {
        Map map = new Map();

        assertTrue(map.doesConnectionExist("Madrid", "Paris"));
        assertFalse(map.doesConnectionExist("London", "Paris"));
    }
}