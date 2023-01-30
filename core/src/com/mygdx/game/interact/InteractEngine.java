package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.interact.cooking_stations.BakingStation;
import com.mygdx.game.interact.cooking_stations.CookingStation;
import com.mygdx.game.interact.cooking_stations.CuttingStation;
import com.mygdx.game.interact.ingredient_stations.*;
import com.mygdx.game.interact.special_stations.Bin;
import com.mygdx.game.interact.special_stations.Counter;
import com.mygdx.game.interact.special_stations.CustomerCounter;
import com.mygdx.game.interact.special_stations.assembly_stations.BurgerStation;
import com.mygdx.game.interact.special_stations.assembly_stations.SaladStation;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;

import java.util.LinkedList;

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
	static LinkedList<CustomerCounter> customerCounters;

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

			new CustomerCounter(70, 140),
			new CustomerCounter(70, 210),
			new CustomerCounter(70, 280),

			new Counter(70, 350),
			new Counter(70, 70),
			new Counter(420, 420),
			new Counter(420, 0),
			new Counter(490, 420),
			new Counter(490, 0),

			new CookingStation(140, 420),
			new CookingStation(280, 420),
			new BakingStation(210, 420),
			new BakingStation(350, 420),
			new CuttingStation(140, 0),
			new CuttingStation(210, 0),
			new CuttingStation(280, 0),
			new CuttingStation(350, 0),

			new BurgerStation(210, 210),
			new SaladStation(280, 210),

			new PattyStation(350, 210),
			new BunStation(420, 210),
			new LettuceStation(560, 210),
			new TomatoStation(560, 280),
			new OnionStation(560, 140),

			new Bin(560, 420),
			new Bin(560, 0)

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