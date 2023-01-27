package com.mygdx.game.interact.special_stations;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game._convenience.IngredientStack;
import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.ingredient.IngredientTextures;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;

/**
 * @author Thomas McCarthy
 *
 * A counter is used for placing ingredients. It has no processes.
 */
public class Counter extends InteractableBase {

    IngredientName storedIngredient;


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public Counter(float xPos, float yPos)
    {
        super(xPos, yPos, "counter.png");
        storedIngredient = null;
    }


    //==========================================================\\
    //                      INTERACTION                         \\
    //==========================================================\\

    @Override
    public void handleInteraction()
    {
        if(storedIngredient == null)
        {
            storedIngredient = PlayerEngine.getActiveChef().getIngredientStack().pop();
        }
        else
        {
            PlayerEngine.getActiveChef().getIngredientStack().push(storedIngredient);
            storedIngredient = null;
        }
    }


    //==========================================================\\
    //                         GETTERS                          \\
    //==========================================================\\

    @Override
    public Texture getIngredientTexture()
    {
        if(storedIngredient == null)    { return super.indicatorArrow; }
        else                            { return IngredientTextures.getTexture(storedIngredient); }
    }

}
