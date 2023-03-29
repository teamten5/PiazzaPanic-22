package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;
import com.mygdx.game.interact.cooking_stations.BakingStation;
import com.mygdx.game.interact.cooking_stations.CookingStation;
import com.mygdx.game.interact.cooking_stations.CuttingStation;
import com.mygdx.game.interact.ingredient_stations.BunStation;
import com.mygdx.game.interact.ingredient_stations.LettuceStation;
import com.mygdx.game.interact.ingredient_stations.OnionStation;
import com.mygdx.game.interact.ingredient_stations.PattyStation;
import com.mygdx.game.interact.ingredient_stations.TomatoStation;
import com.mygdx.game.interact.special_stations.Bin;
import com.mygdx.game.interact.special_stations.Counter;
import com.mygdx.game.interact.special_stations.CustomerCounter;
import com.mygdx.game.interact.special_stations.assembly_stations.BurgerStation;
import com.mygdx.game.interact.special_stations.assembly_stations.SaladStation;

import java.util.LinkedList;
import org.w3c.dom.Text;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Interact Engine renders the various stations in the kitchen, alongside
 * the interaction UI and the timers
 *
 */

public class InteractEngine {

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

	public static void initialise()
	{

		interactables = new InteractableBase[] {

			new CustomerCounter(1, 2),
			new CustomerCounter(1, 3),
			new CustomerCounter(1, 4),

			new Counter(1, 5),
			new Counter(1, 1),
			new Counter(6, 6),
			new Counter(6, 0),
			new Counter(7, 6),
			new Counter(7, 0),

			new CookingStation(2, 6),
			new CookingStation(4, 6),
			new BakingStation(3, 6),
			new BakingStation(5, 6),
			new CuttingStation(2, 0),
			new CuttingStation(3, 0),
			new CuttingStation(4, 0),
			new CuttingStation(5, 0),

			new BurgerStation(3, 3),
			new SaladStation(4, 3),

			new PattyStation(5, 3),
			new BunStation(6, 3),
			new LettuceStation(8, 3),
			new TomatoStation(8, 4),
			new OnionStation(8, 2),

			new Bin(8, 6),
			new Bin(8, 0)

		};

		interactRange = 85f;

		sliderBackground = new Texture("textures/slider_background.png");
		sliderFill = new Texture("textures/slider_fill.png");

		Rectangle[] collisionRects = new Rectangle[interactables.length];
		for(int i=0; i<interactables.length; i++)
		{
			collisionRects[i] = interactables[i].getCollisionRect();
		}
		PlayerEngine.setColliders(collisionRects);
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void update(float delta)
	{
		for(InteractableBase interactable : interactables) {
			// Increment the interactable's timer by the time elapsed between now and the last frame render
			interactable.incrementTime(delta);
		}
	}

	public static void render(SpriteBatch batch) {
		for(InteractableBase interactable : interactables) {
			interactable.render(batch);
			batch.draw(interactable.getIngredientTexture(), interactable.getXPos(), interactable.getYPos(), 1, 1);
			// Render a progress slider above the element if it is currently preparing
			if(interactable.isPreparing())
			{
				float progressWidth = (float) (interactable.getCurrentTime() / interactable.getPreparationTime() * 0.65);
				batch.draw(sliderBackground, interactable.getXPos(), interactable.getYPos() + 0.7f, 0.7f, 0.2f);
				batch.draw(sliderFill, interactable.getXPos(), interactable.getYPos() + 0.725f, progressWidth, 0.15f);
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

		float minDistance = Float.MAX_VALUE;
		InteractableBase closestInteractable = null;
		for(InteractableBase interactable : interactables)
		{
			boolean valid = interactable.tryInteraction(xPos, yPos, interactRange);

			if(valid)
			{
				float xDist = interactable.getXPos() - PlayerEngine.getActiveChef().getXPos();
				float yDist = interactable.getYPos() - PlayerEngine.getActiveChef().getYPos();
				float distance = (float)Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
				if(distance < minDistance)
				{
					minDistance = distance;
					closestInteractable = interactable;
				}
			}
		}
		if(closestInteractable != null)
		{
			closestInteractable.handleInteraction();
		}

		System.out.println("INTERACTION ENDED");
	}
}