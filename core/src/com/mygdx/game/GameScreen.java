package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
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

	OrthographicCamera camera;
	Viewport viewport;
	final static float WORLD_WIDTH = 1600;
	final static float WORLD_HEIGHT = 1200;

	
	//==========================================================\\
	//                         START                            \\
	//==========================================================\\
	@Override
	public void show() {
		stage = new Stage();

		// Set up camera
		float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		camera = new OrthographicCamera();
		viewport = new FitViewport(WORLD_WIDTH * aspectRatio, WORLD_HEIGHT * aspectRatio);
		viewport.apply();
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);

		// Create processor to handle user input
		Gdx.input.setInputProcessor(stage);
		batch = new SpriteBatch();

		PlayerEngine.initialise(batch);
		InteractEngine.initialise(batch);
	}

	
	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\
	@Override
	public void render(float delta) {
		
		// Clear the screen and begin drawing process
		Gdx.gl.glClearColor(1, 1, 1, 0);
		ScreenUtils.clear(1, 1, 1, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		stage.draw();
				
		// Update the render
		PlayerEngine.update();
		InteractEngine.update();
		
		// End the process
		batch.end();
	}
	
	
	//==========================================================\\
	//                 OTHER REQUIRED METHODS                   \\
	//==========================================================\\

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
