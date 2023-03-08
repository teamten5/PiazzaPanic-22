package com.mygdx.game.ingredient;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * @author Thomas McCarthy
 *
 * A class storing a hashmap that maps IngredientNames to their corresponding textures
 */
public final class IngredientTextures {

    private static final HashMap<IngredientName, Texture> textures = new HashMap<IngredientName, Texture>()
    {{
        // Texture for the NULL_INGREDIENT. Do not remove, do not use in stations or recipes!
        put(IngredientName.NULL_INGREDIENT, new Texture("ingredient_null.png"));


        // Other ingredients
        put(IngredientName.LETTUCE_UNCUT, new Texture("ingredient_lettuce_uncut.png"));
        put(IngredientName.LETTUCE_CUT, new Texture("ingredient_lettuce_cut.png"));

        put(IngredientName.TOMATO_UNCUT, new Texture("ingredient_tomato_uncut.png"));
        put(IngredientName.TOMATO_CUT, new Texture("ingredient_tomato_cut.png"));

        put(IngredientName.ONION_UNCUT, new Texture("ingredient_onion_uncut.png"));
        put(IngredientName.ONION_CUT, new Texture("ingredient_onion_cut.png"));

        put(IngredientName.PATTY_RAW, new Texture("ingredient_patty_raw.png"));
        put(IngredientName.PATTY_COOKED, new Texture("ingredient_patty_cooked.png"));

        put(IngredientName.BUNS_UNTOASTED, new Texture("ingredient_bun_untoasted.png"));
        put(IngredientName.BUNS_TOASTED, new Texture("ingredient_bun_toasted.png"));


        // Assembled foods
        put(IngredientName.BURGER, new Texture("ingredient_burger.png"));
        put(IngredientName.SALAD, new Texture("ingredient_salad.png"));
    }};


    //==========================================================\\
    //                         GETTERS                          \\
    //==========================================================\\

    public static Texture getTexture(IngredientName ingredientName)
    {
        return textures.get(ingredientName);
    }

}
