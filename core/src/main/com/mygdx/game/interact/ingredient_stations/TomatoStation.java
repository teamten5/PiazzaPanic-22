package com.mygdx.game.interact.ingredient_stations;

import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;

/**
 * @author Thomas McCarthy
 *
 * An ingredient station that gives tomato to the player
 */
public class TomatoStation extends InteractableBase {

    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public TomatoStation(float xPos, float yPos) {
        super(xPos, yPos, "station_tomato.png", IngredientName.TOMATO_UNCUT);
    }
}
