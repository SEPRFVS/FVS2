package fvs.taxe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class TaxeGame extends Game {
	public static final int WIDTH=1280,HEIGHT=720;

	public SpriteBatch batch;
	public BitmapFont font;
	public BitmapFont fontSmall;
	public ShapeRenderer shapeRenderer;
	
	public static boolean spyMode=false;

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
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}

	
}