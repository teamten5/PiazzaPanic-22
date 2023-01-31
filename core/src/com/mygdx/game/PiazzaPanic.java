package com.mygdx.game;

import com.badlogic.gdx.Game;

public class PiazzaPanic extends Game {
	
	// Screens
	GameScreen gameScreen;
	EndScreen endScreen;
	MenuScreen menuScreen;
	
	@Override
	public void create () {
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public void startGame()
	{
		System.out.println("GAME STARTED");
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}

	public void endGame(String displayDetails)
	{
		System.out.println("GAME ENDED");
		endScreen = new EndScreen(this, displayDetails);
		setScreen(endScreen);
	}

	public void goToMenu()
	{
		System.out.println("RETURNED TO MAIN MENU");
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}
}
