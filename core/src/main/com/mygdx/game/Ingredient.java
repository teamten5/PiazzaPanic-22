package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.interact.InteractableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    final public Texture texture;
    final public String _name;
    public Ingredient _plated;
    public Ingredient _unplated;
    public List<InteractableType> _placableOn;

    public Ingredient(Texture texture, String name) {
        this.texture = texture;
        this._name = name;
        _placableOn = new ArrayList<>();
    }

    static public HashMap<String, Ingredient> loadFromJson1( JsonValue jsonIngredients) {
        HashMap<String, Ingredient> ingredientsHashmap = new HashMap<>();
        for (JsonValue jsonIngredient: jsonIngredients) {
            Ingredient ingredient = new Ingredient(
                  new Texture("textures/" + jsonIngredient.getString("texture")),
                  jsonIngredient.name
            );
            ingredientsHashmap.put(jsonIngredient.name, ingredient);
        }

        return ingredientsHashmap;
    }

    static public void loadFromJson3(
          JsonValue jsonIngredients,
          HashMap<String, Ingredient> ingredientsHashmap,
          HashMap<String, InteractableType> interactableTypeHashMap
    ) {
        for (JsonValue jsonIngredient: jsonIngredients) {
            Ingredient current = ingredientsHashmap.get(jsonIngredient.name);
            for (JsonValue jsonModifier : jsonIngredient.get("modifiers")) {
                switch (jsonModifier.name) {
                    case "plated":
                        current._plated = ingredientsHashmap.get(jsonModifier.asString());
                        break;
                    case "unplated":
                        current._unplated = ingredientsHashmap.get(jsonModifier.asString());
                        break;
                    case "place-on":
                        for (String ingredientName: jsonModifier.asStringArray()) {
                            current._placableOn.add(interactableTypeHashMap.get(ingredientName));
                        }
                        break;
                }
            }
        }
    }
}
