package com.mygdx.game.interact.cooking_stations;

import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.ingredient.IngredientMap;

/**
 * @author Thomas McCarthy
 *
 * Station for cutting, inherits properties from the InteractableBase class
 */
public class CuttingStation extends InteractableBase {

    static IngredientMap ingredientMap = new IngredientMap() {{

        put(ingredientHashMap.get("lettuce-uncut"), ingredientHashMap.get("lettuce-cut"));
        put(ingredientHashMap.get("tomato-uncut"), ingredientHashMap.get("tomato-cut"));
        put(ingredientHashMap.get("onion-uncut"), ingredientHashMap.get("onion-cut"));

    }};


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public CuttingStation(float xPos, float yPos) {

        super(xPos, yPos, "textures/station_cutting.png", ingredientMap, 5.0f, true);

    }
}
