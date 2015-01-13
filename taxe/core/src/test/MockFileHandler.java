package test;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglFileHandle;
import com.badlogic.gdx.files.FileHandle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class MockFileHandler implements Files {
    private String assets;

    public MockFileHandler(String assets) {
        this.assets = assets;
    }

    @Override
    public FileHandle getFileHandle(String path, Files.FileType type) {
        throw new NotImplementedException();
    }

    @Override
    public FileHandle classpath(String path) {
        throw new NotImplementedException();
    }

    @Override
    public FileHandle internal(String path) {
        return new LwjglFileHandle(assets + path, Files.FileType.Internal);
    }

    @Override
    public FileHandle external(String path) {
        throw new NotImplementedException();
    }

    @Override
    public FileHandle absolute(String path) {
        throw new NotImplementedException();
    }

    @Override
    public FileHandle local(String path) {
        return new LwjglFileHandle(assets + path, FileType.Local);
    }

    @Override
    public String getExternalStoragePath() {
        return null;
    }

    @Override
    public boolean isExternalStorageAvailable() {
        return false;
    }

    @Override
    public String getLocalStoragePath() {
        return null;
    }

    @Override
    public boolean isLocalStorageAvailable() {
        return false;
    }
}
