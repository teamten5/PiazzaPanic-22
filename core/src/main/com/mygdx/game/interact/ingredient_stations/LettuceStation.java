package com.mygdx.game.interact.ingredient_stations;

import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;

/**
 * @author Thomas McCarthy
 *
 * An ingredient station that gives lettuce to the player.
 */
public class LettuceStation extends InteractableBase {

    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public LettuceStation(float xPos, float yPos) {
        super(xPos, yPos, "station_lettuce.png", IngredientName.LETTUCE_UNCUT);
    }
}
