package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;

public class InteractableType {
    protected float xSize;
    protected float ySize;
    final protected Texture texture;

    public InteractableType(float xSize, float ySize, Texture texture) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
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
                  new Texture("textures/" + jsonInteractable.getString("texture"))
            );
            InteractableTypeHashMap.put(jsonInteractable.name, interactableType);
        }
        return InteractableTypeHashMap;
    }
}