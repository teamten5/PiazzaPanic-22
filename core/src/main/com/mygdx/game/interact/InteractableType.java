package com.mygdx.game.interact;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.Config;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.javatuples.Triplet;

public class InteractableType {
    final public float xSize; // height in world units
    final public float ySize; // width in world units
    final public Texture texture;
    final public int texStartX; // Where the texture starts in pixels (its own) away from left side
    final public int texStartY; // Where the texture starts in pixels (its own) away from bottom side
    final public int texIngredientStartX; // Where the Ingredient texture starts in pixel away from the left side
    final public int texIngredientStartY; // Where the Ingredient texture starts in pixel away from the bottom side
    final public List<Triplet<String, Integer, Integer>> _customerTable;

    public boolean collision;


    public InteractableType(
          float xSize,
          float ySize,
          Texture texture,
          int texStartX,
          int texStartY,
          int texIngredientStartX,
          int texIngredientStartY,
          boolean interactableCollisions,
          List<Triplet<String, Integer, Integer>> customerTable
          ) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.texture = texture;
        this.texStartX = texStartX;
        this.texStartY = texStartY;
        this.texIngredientStartX = texIngredientStartX;

        this.texIngredientStartY = texIngredientStartY;
        collision = interactableCollisions;

        this._customerTable = customerTable;
    }

    public InteractableInLevel instantiate(float xPos, float yPos,
          ArrayList<Combination> combinations, HashMap<Ingredient, Action> actions) {
        return new InteractableInLevel(this, xPos, yPos, combinations, actions);
    }

    static public HashMap<String, InteractableType> loadFromJson2(JsonValue jsonInteractables) {
        HashMap<String, InteractableType> interactableTypeHashMap = new HashMap<>();
        for (JsonValue jsonInteractable: jsonInteractables) {

            int texIngredientStartX = Config.defaultTexIngredientStartX;
            int texIngredientStartY = Config.defaultTexIngredientStartY;
            int texStartX = Config.defaultTexStartX;
            int texStartY = Config.defaultTexStartY;
            boolean interactableCollisions = Config.defaultInteractableCollisions;
            List<Triplet<String, Integer, Integer>> customerTable = new ArrayList<>();

            for (JsonValue jsonModifier: jsonInteractable.get("modifiers")) {
                switch (jsonModifier.name){
                    case "modify-ingredient-placement":
                        texIngredientStartX = jsonModifier.getInt("x");
                        texIngredientStartY = jsonModifier.getInt("y");
                        break;
                    case "modify-texture-origin":
                        texStartX = jsonModifier.getInt("x");
                        texStartY = jsonModifier.getInt("y");
                        break;
                    case "collision":
                        interactableCollisions = jsonModifier.asBoolean();
                        break;
                    case "customer-table":
                        for (JsonValue jsonChair: jsonModifier.get("chairs")) {
                            customerTable.add(new Triplet<>(
                                  jsonChair.getString("type"),
                                  jsonChair.getInt("dx"),
                                  jsonChair.getInt("dy")
                            ));
                        }
                        break;
                    default:
                        Gdx.app.log("JSON/Interactable", "Unknown modifier: " + jsonModifier.name + " on " + jsonInteractable.name);
                        break;


                }
            }
            InteractableType interactableType = new InteractableType(
                  jsonInteractable.getInt("x-size"),
                  jsonInteractable.getInt("y-size"),
                  new Texture("textures/" + jsonInteractable.getString("texture")),
                  texStartX, texStartY, texIngredientStartX, texIngredientStartY,
                  interactableCollisions, customerTable);
            interactableTypeHashMap.put(jsonInteractable.name, interactableType);
        }

        Gdx.app.log("JSON/Interactable", "Created " + interactableTypeHashMap.size() + " InteractableTypes");
        return interactableTypeHashMap;
    }

    @Override
    public String toString() {
        return "InteractableType{}";
    }
}