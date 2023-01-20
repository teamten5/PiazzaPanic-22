package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.ingredient.IngredientMap;
import com.mygdx.game.ingredient.IngredientName;
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

	private Texture texture;
	private IngredientMap ingredientMap;
	private IngredientName outputIngredient;
	private boolean hasIngredient;
	private boolean isIngredientStation;
	private float preparationTime;
	private float currentTime;
	
	
	//==========================================================\\
	//                     CONSTRUCTORS                         \\
	//==========================================================\\
	
	// Cooking Station Constructor takes a texture, an ingredient map, and a given preparation time
	public InteractableBase(String texture, IngredientMap ingredientMap, float preparationTime)
	{
		this.texture = new Texture(texture);
		this.ingredientMap = ingredientMap;
		this.preparationTime = preparationTime;
		this.hasIngredient = false;
		this.isIngredientStation = false;
	}
	
	// Ingredient Station Constructor takes a texture, an output ingredient, and no preparation time
	public InteractableBase(String texture, IngredientName outputIngredient)
	{
		this.texture = new Texture(texture);
		this.outputIngredient = outputIngredient;
		this.hasIngredient = true;
		this.isIngredientStation = true;
	}
	
	
	//==========================================================\\
	//                      INTERACTION                         \\
	//==========================================================\\
	
	public void Interact()
	{
		Player activeChef = PlayerEngine.getActiveChef();
		
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
		// COOKING STATION : can the station take an ingredient?
		else if(ingredientMap.takesIngredient(activeChef.peekIngredient()))
		{
			outputIngredient = ingredientMap.getOutputIngredient(activeChef.popIngredient());
			currentTime = 0f;
			hasIngredient = true;
		}
	}


	//==========================================================\\
	//                         TIMER                            \\
	//==========================================================\\

	public void incrementTime(float timeElapsed)
	{
		if(hasIngredient && isIngredientStation)
		{
			currentTime += timeElapsed;
		}
	}
	
}
