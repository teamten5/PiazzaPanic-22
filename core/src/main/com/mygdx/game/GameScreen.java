package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableType;
import com.mygdx.game.levels.Level;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The GameScreen class handles the main rendering and updating of the game.
 *
 */

public class GameScreen extends InputAdapter implements Screen {

	private final PolygonSpriteBatch batch;
	private final ShapeRenderer shapeRenderer;
	OrthographicCamera camera;
	GameViewport viewport;

	// A timer to track how long the screen has been running
	static float masterTimer;
	private Label timerLabel;

	// A reference to the main game file
	private final PiazzaPanic main;

	private Level currentLevel;

	public GameScreen(
		PiazzaPanic main,
		HashMap<String, Ingredient> ingredientHashMap,
		HashMap<String, InteractableType> interactableTypeHashMap,
		HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap,
		HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap,
		Level level
	) {
		this.currentLevel = level;
		this.main = main;

		// Set up camera
		camera = new OrthographicCamera();
		viewport = new GameViewport(
			15,
			15,
			camera,
			Config.unitWidthInPixels,
			Config.unitHeightInPixels,
			Config.scaling
		);

		// Create processor to handle user input
		batch = new PolygonSpriteBatch();

		shapeRenderer = new ShapeRenderer();

		masterTimer = 0f;

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		BitmapFont font = new BitmapFont();
		labelStyle.font = font;
		labelStyle.fontColor = Color.WHITE;

		timerLabel = new Label("0s", labelStyle);
		timerLabel.setPosition(-1, -1);
		timerLabel.setAlignment(Align.left);




	}

	
	//==========================================================\\
	//                         START                            \\
	//==========================================================\\
	@Override
	public void show() {

	}

	
	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\
	@Override
	public void render(float delta) {
		// Update the level
		currentLevel.update(delta);



		// Clear the screen and begin drawing process
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);



		batch.begin();

		currentLevel.render(batch);

		// End the process
		batch.end();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Line);
		currentLevel.renderShapes(shapeRenderer);
		shapeRenderer.end();

		// Increment the timer and update UI
		masterTimer += Gdx.graphics.getDeltaTime();
		timerLabel.setText((int) masterTimer);
	}
	
	
	//==========================================================\\
	//                 OTHER REQUIRED METHODS                   \\
	//==========================================================\\

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		batch.setProjectionMatrix(camera.combined);
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
