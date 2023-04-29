package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.interact.Action;
import com.mygdx.game.interact.Combination;
import com.mygdx.game.interact.InteractableType;
import com.mygdx.game.levels.LevelType;
import java.util.ArrayList;
import java.util.HashMap;

public class PiazzaPanic extends Game {
	
	// Screens
	GameScreen gameScreen;
	EndScreen endScreen;
	MenuScreen menuScreen;

	HashMap<String, Ingredient> ingredientHashMap;
	HashMap<String, InteractableType> interactableTypeHashMap;
	HashMap<InteractableType, ArrayList<Combination>> combinationsHashmap;
	HashMap<InteractableType, HashMap<Ingredient, Action>> actionHashmap;
	HashMap<String, LevelType> levelTypeHashMap;
	
	@Override
	public void create () {
		Gdx.app.setLogLevel(Config.loggingLevel);
		loadJson();
		menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	public void startGame()
	{
		System.out.println("GAME STARTED");
		gameScreen = new GameScreen(this, ingredientHashMap, interactableTypeHashMap, combinationsHashmap, actionHashmap, levelTypeHashMap.get("arcade-salad").instantiate());
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
		ingredientHashMap = Ingredient.loadFromJson1(
			jsonRoot.get("ingredients")
		);
		interactableTypeHashMap = InteractableType.loadFromJson2(
			jsonRoot.get("interactables")
		);
		Ingredient.loadFromJson3(
			jsonRoot.get("ingredients"),
			ingredientHashMap,
			interactableTypeHashMap
		);
		combinationsHashmap = Combination.loadFromJson(
			jsonRoot.get("combinations"),
			jsonRoot.get("interactables"),
			ingredientHashMap,
			interactableTypeHashMap
		);
		actionHashmap = Action.	loadFromJson(
			jsonRoot.get("actions"),
			ingredientHashMap,
			interactableTypeHashMap);
		levelTypeHashMap = LevelType.loadFromJson(
			jsonRoot.get("levels"),
			interactableTypeHashMap,
			combinationsHashmap,
			actionHashmap
		);
	}
}
