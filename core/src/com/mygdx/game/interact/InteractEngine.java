package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interact.cooking_stations.BakingStation;
import com.mygdx.game.interact.cooking_stations.CookingStation;
import com.mygdx.game.interact.cooking_stations.CuttingStation;
import com.mygdx.game.interact.ingredient_stations.BunStation;
import com.mygdx.game.interact.ingredient_stations.LettuceStation;
import com.mygdx.game.interact.ingredient_stations.PattyStation;
import com.mygdx.game.interact.special_stations.Bin;
import com.mygdx.game.interact.special_stations.Counter;
import com.mygdx.game.interact.special_stations.assembly_stations.BurgerStation;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Interact Engine renders the various stations in the kitchen, alongside
 * the interaction UI and the timers
 *
 */

public final class InteractEngine {

	static SpriteBatch batch;

	// An array of interactable objects on the screen
	static InteractableBase[] interactables;

	// Determines how far away the player must be to interact with a station
	static float interactRange;

	// Textures used for rendering the Progress Slider
	static Texture sliderBackground;
	static Texture sliderFill;


	//==========================================================\\
	//                      INITIALISER                         \\
	//==========================================================\\

	public static void initialise(SpriteBatch gameBatch)
	{
		batch = gameBatch;

		interactables = new InteractableBase[] {

			new LettuceStation(70, 210),
			new BunStation(70, 280),
			new PattyStation(70, 350),

			new CookingStation(280, 350),
			new CookingStation(350, 350),

			new CuttingStation(280, 210),
			new CuttingStation(350, 210),

			new BakingStation(280, 70),
			new BakingStation(350, 70),

			new Counter(560, 350),
			new Counter(560, 280),
			new Counter(560, 210),

			new BurgerStation(420, 70),

			new Bin(560, 70)

		};

		interactRange = 50f;

		sliderBackground = new Texture("slider_background.png");
		sliderFill = new Texture("slider_fill.png");
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void update()
	{
		for(InteractableBase interactable : interactables) {
			// Render the interactable and the ingredient on it
			batch.draw(interactable.getTexture(), interactable.getXPos(), interactable.getYPos());
			Sprite ingredientSprite = interactable.getIngredientSprite();
			ingredientSprite.setPosition(interactable.getXPos(), interactable.getYPos());
			ingredientSprite.draw(batch);

			// Increment the interactable's timer by the time elapsed between now and the last frame render
			interactable.incrementTime(Gdx.graphics.getDeltaTime());

			// Render a progress slider above the element if it is currently preparing
			if(interactable.isPreparing())
			{
				int progressWidth = (int)(interactable.getCurrentTime() / interactable.getPreparationTime() * 65);
				batch.draw(sliderBackground, interactable.getXPos(), interactable.getYPos() + 70, 70, 20);
				batch.draw(sliderFill, interactable.getXPos(), interactable.getYPos() + 72.5f, progressWidth, 15);
			}
		}
	}


	//==========================================================\\
	//                   INTERACTION CHECK                      \\
	//==========================================================\\

	/*
		Checks for and initiates an interaction if possible.
		This script is NOT responsible for checking for user input.
		User input is handled by the PlayerEngine, which calls this
		method when required.
		Since the PlayerEngine updates before the InteractEngine,
		the interaction is registered on the frame that the key
		is pressed.
		See PlayerEngine > update() for more.
	 */
	public static void interact()
	{
		Player activeChef = PlayerEngine.getActiveChef();
		float xPos = activeChef.getXPos();
		float yPos = activeChef.getYPos();

		System.out.println("\n==============================\nINTERACTION ATTEMPTED");
		for(InteractableBase interactable : interactables)
		{
			boolean interacted = interactable.tryInteraction(xPos, yPos, interactRange);

			// Prevent the player from interacting with multiple interactables at once
			if(interacted) break;
		}
		System.out.println("INTERACTION ENDED");
	}
}