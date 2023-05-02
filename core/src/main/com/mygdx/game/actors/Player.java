package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.mygdx.game.Config;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.Interactable;
import com.mygdx.game.levels.Level;
import com.mygdx.game.actors.controllers.Controller;

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

	public PlayerType type;
	// The LinkedList is used as an implementation of a stack
	public Ingredient carrying;

	public Controller controller;

	private Level level;


	//==========================================================\\
	//                      CONSTRUCTOR                         \\
	//==========================================================\\
	
	public Player(PlayerType type, Controller controller, Level level)
	{
		this.type = type;
		this.level = level;
		this.controller = controller;
		this.posX = type.spawn.posX;
		this.posY = type.spawn.posY;
		this.carrying = null;
	}

	public void render(Batch batch) {
		batch.draw(type.texture, posX, posY, (float)type.texture.getWidth() / Config.unitWidthInPixels, (float)type.texture.getHeight() / Config.unitHeightInPixels);
		Ingredient ingredient = carrying;
		if (ingredient != null) {
			batch.draw(ingredient.texture, posX, posY + 1.1f, 0.7f, 0.7f);
		}
	}

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
			Interactable closestStation = level.interactableAt(posX + controller.facingX + type.sizeX / 2, posY + controller.facingY
				+ type.sizeY / 2);
			if (closestStation != null) {
				closestStation.handleCombination(this);
			}
		}
		if (controller.doAction) {
			Interactable closestStation = level.interactableAt(posX + controller.facingX + type.sizeX / 2, posY + controller.facingY
				+ type.sizeY / 2);
			if (closestStation != null) {
				closestStation.doAction(this);
			}
		}
	}

	Boolean isPositionValid(float x, float y) {
		boolean bl = false, br = false, tl = false, tr = false;
		for (Rectangle rect: level.type.chefValidAreas) {
			if (rect.contains(x,y)) {bl = true;}
			if (rect.contains(x + type.sizeX,y)) {br = true;}
			if (rect.contains(x,y + type.sizeY)) {tl = true;}
			if (rect.contains(x + type.sizeX,y + type.sizeY)) {tr = true;}
		}
		return bl && br && tl && tr;
	}

	public JsonValue saveGame() {
		JsonValue saveData = new JsonValue(ValueType.object);

		saveData.addChild("type", new JsonValue(type.name));
		saveData.addChild("x", new JsonValue(posX));
		saveData.addChild("y", new JsonValue(posY));
		saveData.addChild("carrying", new JsonValue(carrying._name));
		saveData.addChild("controller", controller.saveGame());

		return saveData;
	}
}
