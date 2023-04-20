package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game.player.Player;
import com.mygdx.game.player.PlayerEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The base script for interactable objects
 *
 */

public class Interactable {



	protected Texture indicatorArrow = new Texture("textures/indicator_arrow.png");
	// Ingredient Information

	final private InteractableInLevel instanceOf;


	private Ingredient currentIngredient = null;
	private Action currentAction = null;
	private int actionProgress = 0;
	private boolean chefDoingAction = false;

	
	//==========================================================\\
	//                     CONSTRUCTORS                         \\
	//==========================================================\\
	
	// Cooking Station Constructor takes a texture, an ingredient map, and a given preparation time
	public Interactable(InteractableInLevel instanceOf)
	{
		this.instanceOf = instanceOf;
	}
	
	
	//==========================================================\\
	//                      INTERACTION                         \\
	//==========================================================\\

	// The active chef can only engage with an interactable if they are within the right range
	public boolean tryInteraction(float chefXPos, float chefYPos, final float interactRange)
	{
		float xDist = Math.abs(chefXPos - instanceOf.xPos);
		float yDist = Math.abs(chefYPos - instanceOf.yPos);

		// If chef is within range, handle the appropriate interaction
		return xDist <= interactRange && yDist <= interactRange;
	}

	// We need to determine what action to take based on the interactable's variables
	public void handleInteraction()
	{
		Player activeChef = PlayerEngine.getActiveChef();

		System.out.println("Chef has ingredient " + activeChef.getCurrentIngredient());

		for (Combination combination: instanceOf.combinations) {
			System.out.println(activeChef.carrying == combination.startingChefCarrying);
			System.out.println(activeChef.carrying);
			System.out.println(combination.startingChefCarrying);
			if (
				activeChef.carrying == combination.startingChefCarrying &&
				currentIngredient == combination.startingOnStation
			) {
				System.out.println(combination.endingChefCarrying);
				activeChef.carrying = combination.endingChefCarrying;
				currentIngredient = combination.endingOnStation;

				currentAction = instanceOf.actions.get(currentIngredient);
				if (combination.resetTime) {
					actionProgress = 0;
				}

				return;
			}
		}
	}


	//==========================================================\\
	//                         TIMER                            \\
	//==========================================================\\

	// Increments the counter for the station if required
	public void update(float timeElapsed)
	{
		actionProgress += timeElapsed;
	}

	//==========================================================\\
	//                         RENDER                           \\
	//==========================================================\\

	public void renderBottom(PolygonSpriteBatch batch) {
		batch.draw(instanceOf.type.texture, instanceOf.xPos, instanceOf.yPos - 10f/22f, 1, 1, 0, 10, 32, 22, false, false);

	}

	public void renderTop(PolygonSpriteBatch batch) {
		batch.draw(instanceOf.type.texture, instanceOf.xPos, instanceOf.yPos + 12f/22f, 1, 10f/22f, 0, 0, 32, 10, false, false);

	}


	//==========================================================\\
	//                         GETTERS                          \\
	//==========================================================\\

	public float getXPos() { return instanceOf.xPos; }

	public float getYPos() { return instanceOf.yPos; }

	public Texture getIngredientTexture() {
		if (currentIngredient == null) {
			return indicatorArrow;
		} else {
			return currentIngredient.texture;
		}
	}

	public int getCurrentTime() { return actionProgress; }

	public float getPreparationTime() { return currentAction.timeToComplete; }

	public boolean isPreparing() {
		return currentAction != null;
	}

	public Rectangle getCollisionRect() {
		return new Rectangle(instanceOf.xPos, instanceOf.yPos, instanceOf.type.xSize, instanceOf.type.ySize);
	}

}