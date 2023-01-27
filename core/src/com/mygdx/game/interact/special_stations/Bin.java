package com.mygdx.game.interact.special_stations;

import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;

/**
 * @author Thomas McCarthy
 *
 * An interactable class that allows the user to dispose of the active
 * chef's top stack ingredient.
 */
public class Bin extends InteractableBase {

    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public Bin(float xPos, float yPos)
    {
        super(xPos, yPos, "bin.png");
    }


    //==========================================================\\
    //                      INTERACTION                         \\
    //==========================================================\\

    @Override
    public void handleInteraction()
    {
        PlayerEngine.getActiveChef().getIngredientStack().pop();
    }

}
