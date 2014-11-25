package uk.ac.york.cs.sepr.fvs.taxe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

@SuppressWarnings("unused")

/*public class TaxeGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("gamemap.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0, 1366, 768);
		batch.end();
	}
}*/

public class TaxeGame extends Game{
	
	public static final String TITLE = "TaxE";
	public static final int WIDTH=1366,HEIGHT=768;

	@Override
	public void create() {
		setScreen(new Map());
		
	}
	
}