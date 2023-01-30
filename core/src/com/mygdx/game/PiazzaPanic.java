package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PiazzaPanic extends Game {
	
	Viewport viewport;
	OrthographicCamera camera;
	final static float WORLD_WIDTH = 1000;
	final static float WORLD_HEIGHT = 500;
	
	// Screens
	Screen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
