package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game._convenience.IngredientStack;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Player class stores all information regarding a chef, and also handles player movement.
 *
 */
public class Player {
	
	// player speed is a hard-coded value, so that it is the same for multiple players
	private float speed = 150.0f;
	private int id;
	private float posX;
	private float posY;
	private float previousPosX;
	private float previousPosY;
	private Rectangle collisionRect;
	private Sprite sprite;
	// The LinkedList is used as an implementation of a stack
	private IngredientStack carryStack;
	// Determines if the player is able to move
	private boolean movementEnabled;


	//==========================================================\\
	//                      CONSTRUCTOR                         \\
	//==========================================================\\
	
	public Player(int id, int startX, int startY, String texture)
	{
		this.id = id;
		this.posX = startX;
		this.posY = startY;
		this.sprite = new Sprite(new Texture(texture));
		this.carryStack = new IngredientStack();
		this.movementEnabled = true;

		previousPosX = startX;
		previousPosY = startY;
		collisionRect = new Rectangle(posX, posY, sprite.getTexture().getWidth() * 0.75f, sprite.getTexture().getHeight() * 0.75f);
	}
	
	
	//==========================================================\\
	//                    PLAYER MOVEMENT                       \\
	//==========================================================\\
	
	public void handleMovement(Rectangle[] colliders) {
		sprite.setCenter(getXPos() + sprite.getTexture().getWidth() / 2f, getYPos() + sprite.getTexture().getHeight() / 2f);

		if(movementEnabled) {
			// Check for user movement input
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {moveY(1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {moveY(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {moveX(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {moveX(1f);}

			collisionRect.setPosition(posX, posY);

			for(Rectangle c : colliders)
			{
				if(c.overlaps(collisionRect))
				{
					posX = previousPosX;
					posY = previousPosY;
					collisionRect.setPosition(posX, posY);
				}
			}
		}
	}
	
	public void moveX(float multiplier) {
		previousPosX = posX;
		posX += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	public void moveY(float multiplier) {
		previousPosY = posY;
		posY += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public int getID() { return id; }
	
	public float getXPos() { return posX; }
	
	public float getYPos() { return posY; }
	
	public Sprite getSprite() { return sprite; }

	public IngredientStack getIngredientStack() { return carryStack; }

	public void setMovementEnabled(boolean movementEnabled) {
		this.movementEnabled = movementEnabled;
		System.out.println("CHEF " + (getID() + 1) + " MOVEMENT SET TO " + movementEnabled);
	}
	
}
