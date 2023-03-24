package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;

public class PiazzaPanic extends Game {
	
	// Screens
	GameScreen gameScreen;
	EndScreen endScreen;
	MenuScreen menuScreen;

	HashMap<String, Ingredient> ingredientHashMap;
	
	@Override
	public void create () {
		loadJson();
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

	private void loadJson() {
		JsonReader jsonReader = new JsonReader();
		JsonValue jsonRoot = jsonReader.parse(Gdx.files.internal("data/base.json"));
		ingredientHashMap = Ingredient.loadFromJson(jsonRoot.get("ingredients"));


	}
}
