package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.customer.CustomerEngine;
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

	// A timer to track how long the screen has been running
	static float masterTimer;
	private Label timerLabel;

	// A reference to the main game file
	private PiazzaPanic main = null;


	public GameScreen(PiazzaPanic main)
	{
		super();
		this.main = main;
	}

	
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

		// Initialise Engine scripts
		PlayerEngine.initialise(batch);
		CustomerEngine.initialise(batch);
		InteractEngine.initialise(batch);

		masterTimer = 0f;

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		BitmapFont font = new BitmapFont();
		labelStyle.font = font;
		labelStyle.fontColor = Color.BLACK;

		timerLabel = new Label("0s", labelStyle);
		timerLabel.setPosition(10, Gdx.graphics.getHeight() - 20);
		timerLabel.setAlignment(Align.left);
		stage.addActor(timerLabel);
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
		CustomerEngine.update();

		// End the process
		batch.end();

		// Increment the timer and update UI
		masterTimer += Gdx.graphics.getDeltaTime();
		timerLabel.setText((int) masterTimer);

		// Check for game over state
		if(CustomerEngine.getCustomersRemaining() == 0 && main != null)
		{
			main.endGame("SCENARIO COMPLETED IN\n" + String.valueOf((int)masterTimer) + " seconds");
		}
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
