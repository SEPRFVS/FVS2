package fvs.taxe;

import screencapture.ScreenRecorder;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class TaxeGame extends Game {
	
	//public static final int WIDTH=1366,HEIGHT=768;
	// Using native res of the map image we are using at the moment
	public static final int WIDTH=1022,HEIGHT=678;

	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont fontSmall;
	public ShapeRenderer shapeRenderer;
	
	//ScreenRecorder
	public ScreenRecorder screenRecorder;

	@Override
	public void create() {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		//create font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("arial.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		// font size 50 pixels
		parameter.size = 50;
		font = generator.generateFont(parameter);
		parameter.size = 20;
		fontSmall = generator.generateFont(parameter);
		generator.dispose(); // don't forget to dispose to avoid memory leaks!

		setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); //important!
		
		//Screen Recorder
		if(Gdx.input.isKeyPressed(Input.Keys.R)) {
			//Run screencapture
			screenRecorder = new ScreenRecorder(30,4);
		}else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			//Take single screenshot
			screenRecorder = new ScreenRecorder(1,1);
		}
		if(screenRecorder != null) {
			if(screenRecorder.takeScreenshot() == false) {
				screenRecorder = null;
			}
		}
		
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}

	
}