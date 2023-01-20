package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The PlayerEngine class creates and renders the games' three chefs. It also handles
 * chef switching and general interaction.
 *
 */
public final class PlayerEngine {
	
	static Player[] chefs;
	static Player activeChef;
	
	
	//==========================================================\\
	//                      INITIALISER                         \\
	//==========================================================\\

	public static void initialise()
	{
		chefs = new Player[3];
		chefs[0] = new Player(0,  25,  25, "temp_chef_1.png");
		chefs[1] = new Player(1,  75,  75, "temp_chef_2.png");
		chefs[2] = new Player(2, 125, 125, "temp_chef_3.png");
		activeChef = chefs[0];
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void update(SpriteBatch batch) 
	{
		for(Player chef : chefs) {
			batch.draw(chef.getTexture(), chef.getXPos(), chef.getYPos());
		}
		
		activeChef.handleMovement();
		
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
		else if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			activeChef = chefs[2]; 
		}
	}
	
	
	//==========================================================\\
	//                         GETTERS                          \\
	//==========================================================\\
	
	public static Player getActiveChef() { return activeChef; }
	
}
