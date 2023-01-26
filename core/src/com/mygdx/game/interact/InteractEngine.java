package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interact.cooking_stations.CookingStation;
import com.mygdx.game.interact.cooking_stations.CuttingStation;
import com.mygdx.game.interact.ingredient_stations.LettuceStation;
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

	public static void initialise()
	{
		interactables = new InteractableBase[] {

			new LettuceStation( 50, 200),

			new CuttingStation(200, 200),
			new CuttingStation(350, 200),

			new CookingStation(200, 400),
			new CookingStation(350, 400)

		};

		interactRange = 50f;

		sliderBackground = new Texture("slider_background.png");
		sliderFill = new Texture("slider_fill.png");
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void update(SpriteBatch batch) 
	{
		for(InteractableBase interactable : interactables) {
			// Render the interactable and the ingredient on it
			batch.draw(interactable.getTexture(), interactable.getXPos(), interactable.getYPos());
			batch.draw(interactable.getIngredientTexture(), interactable.getXPos(), interactable.getYPos());

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