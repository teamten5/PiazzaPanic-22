package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
	private Texture texture;
	// The LinkedList is used as an implementation of a stack
	private LinkedList<IngredientName> carryStack;
	

	//==========================================================\\
	//                      CONSTRUCTOR                         \\
	//==========================================================\\
	
	public Player(int id, int startX, int startY, String texture)
	{
		this.id = id;
		this.posX = startX;
		this.posY = startY;
		this.texture = new Texture(texture);
		this.carryStack = new LinkedList<IngredientName>();
	}
	
	
	//==========================================================\\
	//                    PLAYER MOVEMENT                       \\
	//==========================================================\\
	
	public void handleMovement() {
		// Check for user movement input
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {moveY(1f);}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {moveY(-1f);}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {moveX(-1f);}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {moveX(1f);}
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
	}
	
	// Returns the top element of the carry stack
	public IngredientName peekIngredient()
	{
		return carryStack.get(carryStack.size() - 1);
	}
	
	// Removes the top element of the carry stack and returns it
	public IngredientName popIngredient()
	{
		IngredientName ingredient = peekIngredient();
		carryStack.remove(carryStack.size() - 1);
		return ingredient;
	}
	
	
	//==========================================================\\
	//                         GETTERS                          \\
	//==========================================================\\
	
	public int getID() { return id; }
	
	public float getXPos() { return posX; }
	
	public float getYPos() { return posY; }
	
	public Texture getTexture() { return texture; }
	
}
