package com.mygdx.game.interact.special_stations.assembly_stations;

import com.mygdx.game.Ingredient;

/**
 * @author Thomas McCarthy
 *
 * Assembly station for a Burger
 */
public class BurgerStation extends AssemblyStation {

    // Burger recipe
    Ingredient[] recipe = new Ingredient[] {
          ingredientHashMap.get("buns-toasted"),
          ingredientHashMap.get("patty-cooked"),
          ingredientHashMap.get("buns-toasted")
    };

    Ingredient outputIngredient = ingredientHashMap.get("burger");

    public BurgerStation(float xPos, float yPos) {

        super(xPos, yPos, "textures/station_assembly_burger.png");
        setRecipe(recipe, outputIngredient);

    }
}
