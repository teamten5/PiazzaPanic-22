package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonValue;
import java.util.HashMap;

public class Ingredient {
    final public Texture texture;

    public Ingredient(Texture texture) {
        this.texture = texture;
    }

    static public HashMap<String, Ingredient> loadFromJson( JsonValue jsonIngredients) {
        HashMap<String, Ingredient> ingredientsHashmap = new HashMap<>();
        for (JsonValue jsonIngredient: jsonIngredients) {
            Ingredient ingredient = new Ingredient(
                  new Texture("textures/" + jsonIngredient.getString("texture"))
            );
            ingredientsHashmap.put(jsonIngredient.name, ingredient);
        }
        return ingredientsHashmap;
    }
}
