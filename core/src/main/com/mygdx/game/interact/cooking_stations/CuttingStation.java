package com.mygdx.game.interact.cooking_stations;

import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.ingredient.IngredientMap;

/**
 * @author Thomas McCarthy
 *
 * Station for cutting, inherits properties from the InteractableBase class
 */
public class CuttingStation extends InteractableBase {

    static IngredientMap ingredientMap = new IngredientMap() {{

        put(IngredientName.LETTUCE_UNCUT, IngredientName.LETTUCE_CUT);
        put(IngredientName.TOMATO_UNCUT, IngredientName.TOMATO_CUT);
        put(IngredientName.ONION_UNCUT, IngredientName.ONION_CUT);

    }};


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public CuttingStation(float xPos, float yPos) {

        super(xPos, yPos, "station_cutting.png", ingredientMap, 5.0f, true);

    }
}
