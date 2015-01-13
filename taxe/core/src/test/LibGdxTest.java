package test;

import com.badlogic.gdx.Gdx;
import org.junit.Before;

public class LibGdxTest {
    @Before
    public void setUp() {
        /*
        Gdx.files is public static, it's filled with an instance by LwjglApplication()
        which we're not using here, so for the sake of simplicity, I've made my own.
        */
        Gdx.files = new MockFileHandler("");
    }
}
