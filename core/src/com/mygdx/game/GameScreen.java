package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.interact.InteractEngine;
import com.mygdx.game.player.PlayerEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The GameScreen class handles the main rendering and updating of the game.
 *
 */

public class GameScreen extends InputAdapter implements Screen {
	
	Stage stage;
	SpriteBatch batch;

	
	//==========================================================\\
	//                         START                            \\
	//==========================================================\\
	@Override
	public void show() {
				
		PlayerEngine.initialise();
		InteractEngine.initialise();
				
		stage = new Stage();
		// Create processor to handle user input
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();
	}

	
	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\
	@Override
	public void render(float delta) {
		
		// Clear the screen and begin drawing process
		Gdx.gl.glClearColor(1, 1, 1, 0);
		ScreenUtils.clear(1, 1, 1, 0);
		batch.begin();
		stage.draw();
				
		// Update the render
		PlayerEngine.update(batch);
		InteractEngine.update(batch);
		
		// End the process
		batch.end();
	}
	
	
	//==========================================================\\
	//                 OTHER REQUIRED METHODS                   \\
	//==========================================================\\

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
