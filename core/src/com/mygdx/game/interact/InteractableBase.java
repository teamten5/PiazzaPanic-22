package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ingredient.IngredientMap;
import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.ingredient.IngredientTextures;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The base script for interactable objects
 *
 */

public class InteractableBase {

	private float xPos;
	private float yPos;
	private Texture texture;

	private Texture indicatorArrow = new Texture("indicator_arrow.png");

	// Ingredient Information
	private IngredientMap ingredientMap;
	private IngredientName inputIngredient;
	private IngredientName outputIngredient;
	private boolean hasIngredient;

	// Station Information
	private boolean isIngredientStation;
	private float preparationTime;
	private float currentTime;

	/*
		Some stations require a chef to stand by them while preparing
		an ingredient. In these cases, lockChef is set to True,
		and the locked chef is stored in the connectedChef variable.
	 */
	private boolean lockChef;
	private Player connectedChef;
	
	
	//==========================================================\\
	//                     CONSTRUCTORS                         \\
	//==========================================================\\
	
	// Cooking Station Constructor takes a texture, an ingredient map, and a given preparation time
	public InteractableBase(float xPos, float yPos, String texture, IngredientMap ingredientMap, float preparationTime, boolean lockChef)
	{
		this.isIngredientStation = false;
		this.xPos = xPos;
		this.yPos = yPos;
		this.texture = new Texture(texture);
		this.ingredientMap = ingredientMap;
		this.preparationTime = preparationTime;
		this.hasIngredient = false;
		this.lockChef = lockChef;
		this.connectedChef = null;
	}
	
	// Ingredient Station Constructor takes a texture, an output ingredient, and no preparation time
	public InteractableBase(float xPos, float yPos, String texture, IngredientName outputIngredient)
	{
		this.isIngredientStation = true;
		this.xPos = xPos;
		this.yPos = yPos;
		this.texture = new Texture(texture);
		this.outputIngredient = outputIngredient;
		this.hasIngredient = true;
	}
	
	
	//==========================================================\\
	//                      INTERACTION                         \\
	//==========================================================\\

	// The active chef can only engage with an interactable if they are within the right range
	public boolean tryInteraction(float chefXPos, float chefYPos, final float interactRange)
	{
		float xDist = Math.abs(chefXPos - xPos);
		float yDist = Math.abs(chefYPos - yPos);

		// If chef is within range, handle the appropriate interaction
		if(xDist <= interactRange && yDist <= interactRange)
		{
			handleInteraction();
			return true;
		}

		return false;
	}

	// We need to determine what action to take based on the interactable's variables
	private void handleInteraction()
	{
		Player activeChef = PlayerEngine.getActiveChef();

		System.out.println("Chef has ingredient " + activeChef.peekIngredient());
		
		// INGREDIENT STATION : give the ingredient to the chef
		if(isIngredientStation)
		{
			activeChef.pushIngredient(outputIngredient);
		}
		// COOKING STATION : is an ingredient already being prepared?
		else if(hasIngredient)
		{
			if(currentTime >= preparationTime)
			{
				activeChef.pushIngredient(outputIngredient);
				hasIngredient = false;
			}
		}
		// COOKING STATION : can the station take the chef's top ingredient?
		else if(ingredientMap.takesIngredient(activeChef.peekIngredient()))
		{
			inputIngredient = activeChef.peekIngredient();
			outputIngredient = ingredientMap.getOutputIngredient(activeChef.popIngredient());
			currentTime = 0f;
			hasIngredient = true;

			// Prevent the chef from moving if lockChef is true
			if(lockChef)
			{
				System.out.println(getClass().getSimpleName() + " requires CHEF " + (activeChef.getID() + 1) + "'s attention");
				connectedChef = activeChef;
				connectedChef.setMovementEnabled(false);
			}
		}
	}


	//==========================================================\\
	//                         TIMER                            \\
	//==========================================================\\

	// Increments the counter for the station if required
	public void incrementTime(float timeElapsed)
	{
		if(isPreparing())
		{
			currentTime += timeElapsed;
		}
		else if(lockChef && connectedChef != null)
		{
			System.out.println(getClass().getSimpleName() + " has freed CHEF " + (connectedChef.getID() + 1));
			connectedChef.setMovementEnabled(true);
			connectedChef = null;
		}
	}


	//==========================================================\\
	//                         GETTERS                          \\
	//==========================================================\\

	public float getXPos() { return xPos; }

	public float getYPos() { return yPos; }

	public Texture getTexture() { return texture; }

	public Texture getIngredientTexture() {
		if(!hasIngredient) 						{ return indicatorArrow; }
		else if(currentTime >= preparationTime) { return IngredientTextures.getTexture(outputIngredient); }
		else 									{ return IngredientTextures.getTexture(inputIngredient); }
	}

	public float getCurrentTime() { return currentTime; }

	public float getPreparationTime() { return preparationTime; }

	public boolean isPreparing() { return (hasIngredient && !isIngredientStation && currentTime < preparationTime); }
	
}