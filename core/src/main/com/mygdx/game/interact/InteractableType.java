package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;

public class InteractableType {
    final public float xSize; // height in world units
    final public float ySize; // width in world units
    final public Texture texture;
    final public int texStartX; // Where the texture starts in pixels (its own) away from left side
    final public int texStartY; // Where the texture starts in pixels (its own) away from bottom side
    final public int texIngredientStartX; // Where the Ingredient texture starts in pixel away from the left side
    final public int texIngredientStartY; // Where the Ingredient texture starts in pixel away from the bottom side


    public InteractableType(float xSize, float ySize, Texture texture,
          int texStartX, int texStartY, int texIngredientStartX, int texIngredientStartY) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
        this.texStartX = texStartX;
        this.texStartY = texStartY;
        this.texIngredientStartX = texIngredientStartX;

        this.texIngredientStartY = texIngredientStartY;
    }

    public InteractableInLevel instantiate(float xPos, float yPos,
          ArrayList<Combination> combinations, HashMap<Ingredient, Action> actions) {
        return new InteractableInLevel(this, xPos, yPos, combinations, actions);
    }

    static public HashMap<String, InteractableType> loadFromJson(JsonValue jsonInteractables) {
        HashMap<String, InteractableType> InteractableTypeHashMap = new HashMap<>();
        for (JsonValue jsonInteractable: jsonInteractables) {
            InteractableType interactableType = new InteractableType(
                  jsonInteractable.getInt("x-size"),
                  jsonInteractable.getInt("y-size"),
                  new Texture("textures/" + jsonInteractable.getString("texture")),
                  0, 0, 13, 13);
            InteractableTypeHashMap.put(jsonInteractable.name, interactableType);
        }
        return InteractableTypeHashMap;
    }

}