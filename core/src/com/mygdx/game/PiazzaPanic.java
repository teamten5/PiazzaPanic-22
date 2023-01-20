package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PiazzaPanic extends Game {
	
	SpriteBatch batch;
	Texture img;
	
	// Screens
	Screen gameScreen;
	
	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
