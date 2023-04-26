package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.mygdx.game.Ingredient;
import com.mygdx.game.player.Player;

public class Interactable {



	protected Texture indicatorArrow = new Texture("textures/indicator_arrow.png");
	// Ingredient Information

	final public InteractableInLevel instanceOf;


	private Ingredient currentIngredient = null;
	private Action currentAction = null;
	private float actionProgress = 0;
	private Player playerDoingAction = null;

	
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

	// We need to determine what action to take based on the interactable's variables
	public void handleCombination(Player player) {

		for (Combination combination: instanceOf.combinations) {
			if (
				player.carrying == combination.startingChefCarrying &&
				currentIngredient == combination.startingOnStation
			) {
				player.carrying = combination.endingChefCarrying;
				currentIngredient = combination.endingOnStation;

				currentAction = instanceOf.actions.get(currentIngredient);
				if (combination.resetTime) {
					actionProgress = 0;
				}

				return;
			}
		}
	}
	public void doAction(Player player) {
		playerDoingAction = player;
	}


	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\

	// Increments the counter for the station if required
	public void update(float timeElapsed)
	{

		if (currentAction != null && !(currentAction.chefRequired && playerDoingAction == null)) {
			actionProgress += timeElapsed;
			if (actionProgress > currentAction.timeToComplete) {
				currentIngredient = currentAction.output;
				currentAction = null;

			}
		}
		playerDoingAction = null;
	}

	//==========================================================\\
	//                         RENDER                           \\
	//==========================================================\\

	public void renderBottom(PolygonSpriteBatch batch) {
		batch.draw(instanceOf.type.texture, instanceOf.xPos, instanceOf.yPos, 1, 1, 0, 10, 32, 22, false, false);
		if (currentIngredient != null) {
			batch.draw(currentIngredient.texture, instanceOf.xPos + 13f/32f, instanceOf.yPos + 13f/22f, 0.5f, 9f/22f, 0, 8, 16, 8, false, false);
		}
	}

	public void renderTop(PolygonSpriteBatch batch) {
		batch.draw(instanceOf.type.texture, instanceOf.xPos, instanceOf.yPos + 1, 1, 10f/22f, 0, 0, 32, 10, false, false);
		if (currentIngredient != null) {
			batch.draw(currentIngredient.texture, instanceOf.xPos + 13f/32f, instanceOf.yPos + 1, 0.5f, 7f/22f, 0, 0, 16, 8, false, false);
		}
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
}