package fvs.taxe;

import Util.Sprite;
import Util.SpriteButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
/**
 * 
 * @author Robert Precious <rp825@york.ac.uk>
 * 
 * Change to incorporate actual images and new design. Sprites are images without an action and SpriteButtons are actionable Sprites.
 *
 */
public class MainMenuScreen extends ScreenAdapter {
	TaxeGame game;
	OrthographicCamera camera;
	//New Game Menu Textures
	Texture startgameTexture,loadgameTexture, exitgameTexture, backdropTexture, mapTexture, 
	normalModeTexture, spyModeTexture;
	SpriteButton startgameButton, loadgameButton, exitgameButton,
	normalMode, spyMode;
	Sprite backdrop, map;
	Stage startMenuObjects;

	public MainMenuScreen(final TaxeGame game) {
		this.game = game;
		camera = new OrthographicCamera(TaxeGame.WIDTH, TaxeGame.HEIGHT);
		camera.setToOrtho(false);
		startMenuObjects = new Stage();
		Gdx.input.setInputProcessor(startMenuObjects);

		//NewGame Menu Textures
		startgameTexture = new Texture(Gdx.files.internal("NewGameMenu/start_game.png"));
		loadgameTexture = new Texture(Gdx.files.internal("NewGameMenu/load_game.png"));
		exitgameTexture = new Texture(Gdx.files.internal("NewGameMenu/exit_game.png"));
		backdropTexture = new Texture(Gdx.files.internal("NewGameMenu/menu_backdrop.png"));
		mapTexture = new Texture(Gdx.files.internal("gamemap.png"));


		//NewGame Sprites
		backdrop = new Sprite(440,160,backdropTexture);
		map = new Sprite(0,0,mapTexture);

		//NewGame Sprite Buttons
		startgameButton = new SpriteButton(450, 410, startgameTexture){
			@Override
			protected void onClicked(){
				loadgameButton.toggleVisible();
				exitgameButton.toggleVisible();
				normalMode.toggleVisible();
				spyMode.toggleVisible();
			}
		};

		loadgameButton = new SpriteButton(450, 335, loadgameTexture);

		exitgameButton = new SpriteButton(450, 260, exitgameTexture){
			@Override
			protected void onClicked(){
				Gdx.app.exit();
			}
		};

		//Mode selection
		normalModeTexture = new Texture(Gdx.files.internal("NewGameMenu/normalModeButton.png"));
		spyModeTexture = new Texture(Gdx.files.internal("NewGameMenu/spyModeButton.png"));

		normalMode = new SpriteButton(540, 290, normalModeTexture){
			@Override
			protected void onClicked(){
				game.setScreen(new GameScreen(game));
			}
		};
		
		spyMode = new SpriteButton(660, 290, spyModeTexture){
			@Override
			protected void onClicked(){
				TaxeGame.spyMode=true;
				game.setScreen(new GameScreen(game));
			}
		};
		
		normalMode.setVisible(false);
		spyMode.setVisible(false);

		startMenuObjects.addActor(map);
		startMenuObjects.addActor(backdrop);
		startMenuObjects.addActor(startgameButton);
		startMenuObjects.addActor(loadgameButton);
		startMenuObjects.addActor(exitgameButton);
		
		startMenuObjects.addActor(normalMode);
		startMenuObjects.addActor(spyMode);
	}

	public void draw() {

	}

	@Override
	public void render(float delta) {
		//update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		startMenuObjects.act(Gdx.graphics.getDeltaTime());
		startMenuObjects.draw();

	}
}