package com.mygdx.game.interact;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.mygdx.game.Config;
import com.mygdx.game.Ingredient;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.Spot;
import java.util.ArrayList;
import java.util.List;

public class Interactable {



	protected Texture indicatorArrow = new Texture("textures/indicator_arrow.png");
	// Ingredient Information

	final public InteractableInLevel instanceOf;

	public List<Spot> attachedSpots;
	public Ingredient currentIngredient = null;
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
		// check if any actions should happen with starting item (which is currently always null)
		currentAction = instanceOf.actions.get(currentIngredient);

		attachedSpots = List.copyOf(instanceOf.attachedSpots);
	}

	//==========================================================\\
	//                      INTERACTION                         \\
	//==========================================================\\

	public void handleCombination(Player player) {

		for (Combination combination: instanceOf.combinations) {
			if (
				player.carrying == combination.startingChefCarrying &&
				currentIngredient == combination.startingOnStation
			) {
				player.carrying = combination.endingChefCarrying;
				setIngredient(combination.endingOnStation);

				if (combination.resetTime) {
					actionProgress = 0;
				}

				return;
			}
		}
	}

	public void setIngredient(Ingredient ingredient) {
		currentIngredient = ingredient;
		currentAction = instanceOf.actions.get(currentIngredient);
	}
	public void doAction(Player player) {
		playerDoingAction = player;
	}


	//==========================================================\\
	//                        UPDATE                            \\
	//==========================================================\\

	// Increments the counter for the station if required
	public void update(float timeElapsed) {

		// it's a bit weird this bit of logic is done here
		if (playerDoingAction != null) {
			for (Spot attachedSpot: attachedSpots) {
				if (attachedSpot.occupiedBy != null) {
					attachedSpot.occupiedBy.interactWith(playerDoingAction);
				}
			}
		}
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

	// (float) is used to force floating point division
	// these functions are admittedly a complete mess
	public void renderBottom(PolygonSpriteBatch batch) {
		batch.draw(
			instanceOf.type.texture,
			instanceOf.xPos + (float)instanceOf.type.texStartX / Config.unitWidthInPixels,
			instanceOf.yPos + (float)instanceOf.type.texStartY / Config.unitHeightInPixels,
			(float) instanceOf.type.texture.getWidth() / Config.unitWidthInPixels,
			max(0,min((float)instanceOf.type.texture.getHeight() / Config.unitHeightInPixels, instanceOf.type.ySize - (float)instanceOf.type.texStartY / Config.unitHeightInPixels)),
			0,
			(int) max(0, (instanceOf.type.texture.getHeight() - instanceOf.type.ySize * Config.unitHeightInPixels + instanceOf.type.texStartY)),
			instanceOf.type.texture.getWidth(),
			(int) min(instanceOf.type.texture.getHeight(), Config.unitHeightInPixels * instanceOf.type.ySize - instanceOf.type.texStartY),
			false, false);
		if (currentIngredient != null) {
			batch.draw(
				currentIngredient.texture,
				instanceOf.xPos + (float)(instanceOf.type.texStartX + instanceOf.type.texIngredientStartX) / Config.unitWidthInPixels,
				instanceOf.yPos + (float)(instanceOf.type.texStartY + instanceOf.type.texIngredientStartY) / Config.unitHeightInPixels,
				(float) currentIngredient.texture.getWidth() / Config.unitWidthInPixels,
				max(0, min((float)currentIngredient.texture.getHeight() / Config.unitHeightInPixels, instanceOf.type.ySize - (float)(instanceOf.type.texStartY + instanceOf.type.texIngredientStartY) / Config.unitHeightInPixels)),
				0,
				(int) max(0, (currentIngredient.texture.getHeight() - instanceOf.type.ySize * Config.unitHeightInPixels + instanceOf.type.texStartY + instanceOf.type.texIngredientStartY)),
				currentIngredient.texture.getWidth(),
				(int) min(currentIngredient.texture.getHeight(), (instanceOf.type.ySize * Config.unitHeightInPixels) - instanceOf.type.texStartY - instanceOf.type.texIngredientStartY),
				false, false);
		}
	}

	public void renderTop(PolygonSpriteBatch batch) {
		batch.draw(
			instanceOf.type.texture,
			instanceOf.xPos + (float)instanceOf.type.texStartX / Config.unitWidthInPixels,
			max(instanceOf.yPos + instanceOf.type.ySize, instanceOf.yPos + (float)instanceOf.type.texStartY / Config.unitHeightInPixels),
			(float)instanceOf.type.texture.getWidth() / Config.unitWidthInPixels,
			max(0, min((float)instanceOf.type.texture.getHeight() / Config.unitHeightInPixels, (float)(instanceOf.type.texture.getHeight() + instanceOf.type.texStartY) / Config.unitHeightInPixels - instanceOf.type.ySize)),
			0,
			0,
			instanceOf.type.texture.getWidth(),
			(int) min(instanceOf.type.texture.getHeight(), instanceOf.type.texture.getHeight() - Config.unitHeightInPixels * instanceOf.type.ySize + instanceOf.type.texStartY),
			false,
			false);
		if (currentIngredient != null) {
			batch.draw(
				currentIngredient.texture,
				instanceOf.xPos + (float)(instanceOf.type.texStartX + instanceOf.type.texIngredientStartX) / Config.unitWidthInPixels,
				max(instanceOf.yPos + instanceOf.type.ySize, instanceOf.yPos + (float)(instanceOf.type.texStartY + instanceOf.type.texIngredientStartY) / Config.unitHeightInPixels),
				(float) currentIngredient.texture.getWidth() / Config.unitWidthInPixels,
				max(0, min((float)currentIngredient.texture.getHeight() / Config.unitHeightInPixels, (float)(currentIngredient.texture.getHeight() + instanceOf.type.texStartY + instanceOf.type.texIngredientStartY) / Config.unitHeightInPixels - instanceOf.type.ySize)),
				0,
				0,
				currentIngredient.texture.getWidth(),
				(int) min(currentIngredient.texture.getHeight(), currentIngredient.texture.getHeight() + instanceOf.type.texStartY + instanceOf.type.texIngredientStartY - Config.unitHeightInPixels * instanceOf.type.ySize),
				false, false);
		}
	}


	//==========================================================\\
	//                         GETTERS                          \\
	//==========================================================\\

	public float getXPos() { return instanceOf.xPos; }

	public float getYPos() { return instanceOf.yPos; }
}