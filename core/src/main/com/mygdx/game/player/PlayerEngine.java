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

	
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public static Player getActiveChef() { return activeChef; }

	public static void setColliders(Rectangle[] colliders) { interactableColliders = colliders; }

}