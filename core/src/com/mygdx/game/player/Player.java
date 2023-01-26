package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ingredient.IngredientName;

import java.util.LinkedList;

/**
 * 
 * @author Thomas McCarthy
 * 
 * The Player class stores all information regarding a chef, and also handles player movement.
 *
 */
public class Player {
	
	// player speed is a hard-coded value, so that it is the same for multiple players
	private float speed = 100.0f;
	private int id;
	private float posX;
	private float posY;
	private Sprite sprite;
	// The LinkedList is used as an implementation of a stack
	private LinkedList<IngredientName> carryStack;
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
		sprite.setCenter(sprite.getWidth() / 2f, sprite.getHeight() / 2f);
		this.carryStack = new LinkedList<>();
		this.movementEnabled = true;
	}
	
	
	//==========================================================\\
	//                    PLAYER MOVEMENT                       \\
	//==========================================================\\
	
	public void handleMovement() {
		if(movementEnabled) {
			// Check for user movement input
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {moveY(1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {moveY(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {moveX(-1f);}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {moveX(1f);}
		}
	}
	
	public void moveX(float multiplier) {
		posX += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	public void moveY(float multiplier) {
		posY += Gdx.graphics.getDeltaTime() * multiplier * speed;
	}
	
	
	//==========================================================\\
	//                   INGREDIENT STACK                       \\
	//==========================================================\\
	
	// Adds new ingredient to the carry stack
	public void pushIngredient(IngredientName ingredient)
	{
		carryStack.add(ingredient);
		System.out.println("PUSHED " + ingredient + " TO CHEF " + (getID() + 1) + " STACK");
	}
	
	// Returns the top element of the carry stack
	public IngredientName peekIngredient()
	{
		if(carryStack.size() == 0)
		{
			return IngredientName.NULL_INGREDIENT;
		}

		return carryStack.get(carryStack.size() - 1);
	}

	// Returns the given index of the stack
	public IngredientName peekAtDepth(int depth)
	{
		try
		{
			IngredientName ingredient = carryStack.get(carryStack.size() - depth);
			return ingredient;
		}
		catch(Exception e)
		{
			return IngredientName.NULL_INGREDIENT;
		}
	}
	
	// Removes the top element of the carry stack and returns it
	public IngredientName popIngredient()
	{
		IngredientName ingredient = peekIngredient();
		if(ingredient != IngredientName.NULL_INGREDIENT)
		{
			carryStack.remove(carryStack.size() - 1);
		}

		System.out.println("POPPED " + ingredient + " FROM CHEF " + (getID() + 1) + " STACK");

		return ingredient;
	}
	
	
	//==========================================================\\
	//                    GETTERS & SETTERS                     \\
	//==========================================================\\
	
	public int getID() { return id; }
	
	public float getXPos() { return posX; }
	
	public float getYPos() { return posY; }
	
	public Sprite getSprite() { return sprite; }

	public void setMovementEnabled(boolean movementEnabled) {
		this.movementEnabled = movementEnabled;
		System.out.println("CHEF " + (getID() + 1) + " MOVEMENT SET TO " + movementEnabled);
	}
	
}
