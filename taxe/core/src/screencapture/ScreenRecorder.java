package screencapture;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import java.nio.ByteBuffer;

public class ScreenRecorder {
	private int counter = 0;
	private int targetFPS = 30;
	private int seconds = 4;
	private ArrayList<Pixmap> screenshots = new ArrayList<Pixmap>();
	
	public ScreenRecorder(int targetFPS, int seconds) {
		this.targetFPS = targetFPS;
		this.seconds = seconds;
	}
	
	public boolean takeScreenshot() {
		if(counter == 0){
			System.out.println("Recording");
		}
		if(counter >= 60*seconds) {
			saveScreenshots();
			return false;
		}
		if(Math.floorMod(counter, Math.round(60/targetFPS)) == 0) {
			Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			screenshots.add(pixmap);
		}
		counter++;
		return true;
	}
	
	private void saveScreenshots() {
		int index = 0;
		System.out.println("Processing Images");
		
		int foldernumber = 1;
		if(Gdx.files.external("taxescreenshots").isDirectory()) {
			while(Gdx.files.external("taxescreenshots/" + foldernumber).isDirectory()) {
				foldernumber++;
			}
		}
		
		for(Pixmap pixmap : screenshots) {
			index++;
			
			//Flip
			ByteBuffer pixels = pixmap.getPixels();
			int numBytes = Gdx.graphics.getWidth() * Gdx.graphics.getHeight() * 4;
            byte[] lines = new byte[numBytes];
            int numBytesPerLine = Gdx.graphics.getWidth() * 4;
            for (int i = 0; i < Gdx.graphics.getHeight(); i++) {
                pixels.position((Gdx.graphics.getHeight() - i - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
			
			//Save
			FileHandle fh;
			do {
				fh = Gdx.files.external("taxescreenshots/" + foldernumber + "/screenshot" + index + ".png");
			} while(fh.exists());
			System.out.println("Processing Image " + index);
			PixmapIO.writePNG(fh, pixmap);
			
			//Destroy
			pixmap.dispose();
		}
		System.out.println("Processing Complete");
	}
}
