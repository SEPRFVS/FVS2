package test;

import gameLogic.map.StationHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class StationHelperTest {

    @Test
    public void testDoesConnectionExist() throws Exception {
        assertTrue(StationHelper.doesConnectionExist("Madrid", "Paris"));
        assertFalse(StationHelper.doesConnectionExist("London", "Paris"));
    }
}