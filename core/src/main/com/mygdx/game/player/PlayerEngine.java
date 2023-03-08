package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.ingredient.IngredientTextures;
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

	static SpriteBatch batch;

	static Player[] chefs;
	static Player activeChef;
	static Rectangle[] interactableColliders;

	
	//==========================================================\\
	//                      INITIALISER                         \\
	//==========================================================\\

	public static void initialise(SpriteBatch gameBatch)
	{
		batch = gameBatch;

		chefs = new Player[2];
		chefs[0] = new Player(0,  175,  350, "temp_chef_1.png");
		chefs[1] = new Player(1,  455,  350, "temp_chef_2.png");
		// chefs[2] = new Player(2, 125, 125, "temp_chef_3.png");

		activeChef = chefs[0];

		interactableColliders = new Rectangle[0];
	}
	
	
	//==========================================================\\
	//                         UPDATE                           \\
	//==========================================================\\

	public static void update()
	{
		for(Player chef : chefs) {
			batch.draw(chef.getSprite(), chef.getXPos(), chef.getYPos());

			// Render the top three ingredients of the Chef's carry stack
			for(int i=2; i>-1; i--)
			{
				Sprite ingredientSprite = new Sprite(
					IngredientTextures.getTexture(chef.getIngredientStack().peekAtDepth(3 - i)));
				float size = 30f;

				ingredientSprite.setSize(size, size);
				ingredientSprite.setCenter(ingredientSprite.getWidth() / 2f, ingredientSprite.getHeight() / 2f);

				ingredientSprite.setAlpha(1 - ((2 - i) * 0.45f));

				float xOffset = ((i - 1) * -35f) + (chef.getSprite().getWidth() / 2) - (size / 2f);
				ingredientSprite.setX(chef.getXPos() + xOffset);
				ingredientSprite.setY(chef.getYPos() + 70f);

				ingredientSprite.draw(batch);
			}
		}
		
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