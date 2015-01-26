package test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GdxFileTest extends LibGdxTest {
    @Test
    public void canReadFile() {
        FileHandle file = Gdx.files.internal("stations.json");
        assertTrue(file.length() > 10);
    }
}
