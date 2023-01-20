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
        put(IngredientName.LETTUCE_UNCUT, new Texture("ingredient_lettuce_uncut.png"));
        put(IngredientName.LETTUCE_CUT, new Texture("ingredient_lettuce_cut.png"));
    }};


    //==========================================================\\
    //                         GETTERS                          \\
    //==========================================================\\

    public static Texture getTexture(IngredientName ingredientName)
    {
        return textures.get(ingredientName);
    }

}
