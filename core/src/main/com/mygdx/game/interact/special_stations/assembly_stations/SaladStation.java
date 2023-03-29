package com.mygdx.game.interact.special_stations.assembly_stations;

import com.mygdx.game.Ingredient;

/**
 * @author Thomas McCarthy
 *
 * Assembly station for a Burger
 */
public class SaladStation extends AssemblyStation {

    // Salad recipe
    Ingredient[] recipe = new Ingredient[] {
            ingredientHashMap.get("lettuce-cut"),
            ingredientHashMap.get("onion-cut"),
            ingredientHashMap.get("tomato-cut")
    };

    Ingredient outputIngredient = ingredientHashMap.get("salad");

    public SaladStation(float xPos, float yPos) {

        super(xPos, yPos, "textures/station_assembly_salad.png");
        setRecipe(recipe, outputIngredient);

    }
}
