package com.mygdx.game.ingredient;

import com.mygdx.game.Ingredient;
import java.util.HashMap;

/**
 * @author Thomas McCarthy
 *
 * The IngredientMap is an extension to the HashMap class, mapping input ingredients to output
 * ingredients. It exists mostly for readability, and is used primarily by Cooking Stations.
 *
 * It is essentially a renaming/refactoring of HashMap<IngredientName, IngredientName>
 */

public class IngredientMap extends HashMap<Ingredient, Ingredient> {

    public boolean takesIngredient(Ingredient ingredient)
    {
        return super.containsKey(ingredient);
    }

    public Ingredient getOutputIngredient(Ingredient ingredient)
    {
        return super.get(ingredient);
    }

}
