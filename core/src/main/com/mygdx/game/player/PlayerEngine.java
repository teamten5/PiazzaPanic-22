package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.InteractEngine;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The PlayerEngine class creates and renders the games' three chefs. It also handles
 * chef switching and detects interaction input (although the handling of interactions
 * is then done by the InteractEngine)
 *
 */
public final class PlayerEngine {

	static Player[] chefs;
	static Player activeChef;
	static Rectangle[] interactableColliders;

	
	//==========================================================\\
	//                      INITIALISER                         \\
	//==========================================================\\

	public static void initialise()
	{

		chefs = new Player[2];
		chefs[0] = new Player(0,  2,  5, "textures/temp_chef_1.png");
		chefs[1] = new Player(1,  6,  5, "textures/temp_chef_2.png");
		// chefs[2] = new Player(2, 125, 125, "temp_chef_3.png");

		activeChef = chefs[0];

		interactableColliders = new Rectangle[0];
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void render(SpriteBatch batch) {
		for(Player chef : chefs) {
			batch.draw(chef.getSprite(), chef.getXPos(), chef.getYPos(), 0.8f, 0.8f * 1.5f);
			Ingredient ingredient = chef.getCurrentIngredient();
			if (ingredient != null) {
				batch.draw(ingredient.texture, chef.getXPos(), chef.getYPos() + 1.1f, 0.7f, 0.7f);
			}
		}
	}

	public static void update(float delta) {
		
		activeChef.handleMovement(interactableColliders);
		
		// Chef Quick-Switch with 'Q'
		if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
			activeChef = chefs[(activeChef.getID() + 1) % chefs.length];
		}
		// Chef switch with numbers 1-3
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			activeChef = chefs[0];
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			activeChef = chefs[1];
		}
		/* else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			activeChef = chefs[2]; 
		} */

		// Check for interaction input
		if(Gdx.input.isKeyJustPressed(Input.Keys.E))
		{
			InteractEngine.interact();
		}
	}
	
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public static Player getActiveChef() { return activeChef; }

	public static void setColliders(Rectangle[] colliders) { interactableColliders = colliders; }

}