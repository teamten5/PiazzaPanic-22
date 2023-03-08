package com.mygdx.game.interact.special_stations.assembly_stations;

import com.mygdx.game.ingredient.IngredientName;

/**
 * @author Thomas McCarthy
 *
 * Assembly station for a Burger
 */
public class SaladStation extends AssemblyStation {

    // Salad recipe
    IngredientName[] recipe = new IngredientName[] {
            IngredientName.LETTUCE_CUT,
            IngredientName.ONION_CUT,
            IngredientName.TOMATO_CUT
    };

    IngredientName outputIngredient = IngredientName.SALAD;

    public SaladStation(float xPos, float yPos) {

        super(xPos, yPos, "station_assembly_salad.png");
        setRecipe(recipe, outputIngredient);

    }
}
