package com.mygdx.game.interact.cooking_stations;

import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.ingredient.IngredientMap;

/**
 * @author Thomas McCarthy
 *
 * Station for cooking, inherits properties from the InteractableBase class
 */
public class CookingStation extends InteractableBase {

    static IngredientMap ingredientMap = new IngredientMap() {{

        put(IngredientName.PATTY_RAW, IngredientName.PATTY_COOKED);

    }};


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public CookingStation(float xPos, float yPos) {

        super(xPos, yPos, "station_cooking.png", ingredientMap, 8.0f, false);

    }
}
