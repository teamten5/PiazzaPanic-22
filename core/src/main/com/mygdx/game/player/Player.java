package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.levels.Level;
import com.mygdx.game.player.controllers.Controller;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Player class stores all information regarding a chef, and also handles player movement.
 *
 */
public class Player {


	private float posX;
	private float posY;
	private float sizeX;
	private float sizeY;
	private Sprite sprite;
	// The LinkedList is used as an implementation of a stack
	public Ingredient carrying;
	// Determines if the player can move
	private boolean movementEnabled;

	private Controller controller;

	private Level level;


	//==========================================================\\
	//                      CONSTRUCTOR                         \\
	//==========================================================\\
	
	public Player(Controller controller, int startX, int startY, String texture, Level level)
	{
		this.level = level;
		this.controller = controller;
		this.posX = startX;
		this.posY = startY;
		sizeX = 0.7f;
		sizeY = 0.4f;
		this.sprite = new Sprite(new Texture(texture));
		this.carrying = null;
		this.movementEnabled = true;
	}
	
	
	//==========================================================\\
	//                        CONTROLS                          \\
	//==========================================================\\

	public void update(float delta) {
		controller.update(delta);
		float newx = posX + controller.x;
		float newy = posY + controller.y;

		if (isPositionValid(newx, newy)) {
			posX = newx;
			posY = newy;
		} else if (isPositionValid(posX, newy)) {
			posY = newy;
		} else if (isPositionValid(newx, posY)) {
			posX = newx;
		}
		if (controller.doCombination) {
			Interactable closestStation = level.interactableAt(posX + controller.facing_x + sizeX / 2, posY + controller.facing_y + sizeY / 2);
			if (closestStation != null) {
				closestStation.handleCombination(this);
			}
		}
		if (controller.doAction) {
			Interactable closestStation = level.interactableAt(posX + controller.facing_x + sizeX / 2, posY + controller.facing_y + sizeY / 2);
			if (closestStation != null) {
				closestStation.doAction(this);
			}
		}
	}

	Boolean isPositionValid(float x, float y) {
		boolean bl = false, br = false, tl = false, tr = false;
		for (Rectangle rect: level.type.chefValidAreas) {
			if (rect.contains(x,y)) {bl = true;}
			if (rect.contains(x + sizeX,y)) {br = true;}
			if (rect.contains(x,y + sizeY)) {tl = true;}
			if (rect.contains(x + sizeX,y + sizeY)) {tr = true;}
		}
		return bl && br && tl && tr;
	}
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public float getXPos() { return posX; }
	
	public float getYPos() { return posY; }
	
	public Sprite getSprite() { return sprite; }

	public Ingredient getCurrentIngredient() { return carrying; }

	public void setMovementEnabled(boolean movementEnabled) {
		this.movementEnabled = movementEnabled;
		System.out.println("CHEF " + " MOVEMENT SET TO " + movementEnabled);
	}
	
}
