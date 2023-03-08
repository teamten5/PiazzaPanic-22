package com.mygdx.game.ingredient;

import java.util.HashMap;

/**
 * @author Thomas McCarthy
 *
 * The IngredientMap is an extension to the HashMap class, mapping input ingredients to output
 * ingredients. It exists mostly for readability, and is used primarily by Cooking Stations.
 *
 * It is essentially a renaming/refactoring of HashMap<IngredientName, IngredientName>
 */

public class IngredientMap extends HashMap<IngredientName, IngredientName> {

    public boolean takesIngredient(IngredientName ingredientName)
    {
        return super.containsKey(ingredientName);
    }

    public IngredientName getOutputIngredient(IngredientName ingredientName)
    {
        return super.get(ingredientName);
    }

}
