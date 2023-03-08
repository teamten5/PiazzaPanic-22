package com.mygdx.game.interact.ingredient_stations;

import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;

/**
 * @author Thomas McCarthy
 *
 * An ingredient station that gives a bun to the player.
 */
public class BunStation extends InteractableBase {

    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public BunStation(float xPos, float yPos) {
        super(xPos, yPos, "station_bun.png", IngredientName.BUNS_UNTOASTED);
    }
}
