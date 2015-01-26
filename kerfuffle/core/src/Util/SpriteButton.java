package Util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * @author Matthew Taylor <mjkt500@york.ac.uk>
 * @author Rob
 */

public class SpriteButton extends Sprite{

	/**
	 * A sprite which can be clicked on. The onClicked method can be overwritten
	 * using anonymous Java Classes
	 * @param x xPosition on screen
	 * @param y yPosition on screen
	 * @param texture Image texture to use
	 */
	public SpriteButton(float x, float y, Texture texture)
	{
		super(x, y, texture);
		setBounds(x, y, texture.getWidth(), texture.getHeight());
		
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				((SpriteButton)event.getTarget()).onClicked();
				return true;
			}
		});
		
		addListener(new InputListener(){
			public void enter(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
				((SpriteButton)event.getTarget()).onMouseEnter();
			}
		});
		
		addListener(new InputListener(){
			public void exit(InputEvent event, float x, float y, int pointer, Actor ScreenCard) {
				((SpriteButton)event.getTarget()).onMouseExit();
			}
		});
	}
	
	/**
	 * Called whenever the Sprite is clicked on. Overwritten using anonymous classes,
	 */
	protected void onClicked(){}
	
	/**
	 * Called upon the mouse hovering over the sprite. Overwritten using anonymous classes.
	 */
	protected void onMouseEnter(){}
	
	/**
	 * Called upon a mouse leaving an image region. Overwritten using anonymous classes
	 */
	protected void onMouseExit(){}
}
