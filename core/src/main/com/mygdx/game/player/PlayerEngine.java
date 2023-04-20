package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.InteractEngine;
import com.mygdx.game.levels.Level;

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

	public static void initialise(Level level)
	{


		chefs = level.players.toArray(new Player[0]);
		activeChef = chefs[0];
		interactableColliders = new Rectangle[0];
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

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