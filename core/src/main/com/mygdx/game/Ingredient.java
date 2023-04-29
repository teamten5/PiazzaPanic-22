package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import com.mygdx.game.interact.InteractableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ingredient {
    final public Texture texture;
    final public String _name;
    public Ingredient _plated;
    public Ingredient _unplated;
    public List<InteractableType> _placableOn;

    public static List<Ingredient> _plates = new ArrayList<>();

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

        Gdx.app.log("JSON/Ingredient", "Created " + ingredientsHashmap.size() + " Ingredients");
        return ingredientsHashmap;
    }

    static public void loadFromJson3(
          JsonValue jsonIngredients,
          HashMap<String, Ingredient> ingredientsHashmap,
          HashMap<String, InteractableType> interactableTypeHashMap
    ) {
        for (JsonValue jsonIngredient: jsonIngredients) {
            Ingredient ingredient = ingredientsHashmap.get(jsonIngredient.name);
            for (JsonValue jsonModifier : jsonIngredient.get("modifiers")) {
                switch (jsonModifier.name) {
                    case "plated":
                        ingredient._plated = ingredientsHashmap.get(jsonModifier.asString());
                        ingredientsHashmap.get(jsonModifier.asString())._unplated = ingredient;

                        break;
                    case "place-on":
                        for (String ingredientName: jsonModifier.asStringArray()) {
                            ingredient._placableOn.add(interactableTypeHashMap.get(ingredientName));
                        }
                        break;
                    case "plate":
                        if (jsonModifier.asBoolean()) {
                            _plates.add(ingredient);
                        }
                        break;
                    default:
                        Gdx.app.log("JSON/Ingredient", "Unknown modifier: " + jsonModifier.name + " on " + jsonIngredient.name);
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Ingredient{" + _name + '}';
    }
}
