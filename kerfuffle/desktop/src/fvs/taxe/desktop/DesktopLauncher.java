package fvs.taxe.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import fvs.taxe.TaxeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//Set window size
		config.height = TaxeGame.HEIGHT;
		config.width = TaxeGame.WIDTH;
		config.title = "TaxE";
		config.resizable = false;
		config.addIcon("icon/256.png", FileType.Internal);
		config.addIcon("icon/128.png", FileType.Internal);
		config.addIcon("icon/64.png", FileType.Internal);
		config.addIcon("icon/32.png", FileType.Internal);
		config.addIcon("icon/16.png", FileType.Internal);
		//config.fullscreen = true;
		new LwjglApplication(new TaxeGame(), config);
	}
}
