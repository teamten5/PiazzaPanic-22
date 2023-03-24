package com.mygdx.game.interact.special_stations;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Ingredient;
import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;


/**
 * @author Thomas McCarthy
 *
 * A counter is used for placing ingredients. It has no processes.
 */
public class Counter extends InteractableBase {

    protected Ingredient storedIngredient;


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
            Ingredient ingredient = PlayerEngine.getActiveChef().getIngredientStack().peek();
            if(ingredient == null)
            {
                storedIngredient = PlayerEngine.getActiveChef().getIngredientStack().pop();
            }
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
        else                            { return storedIngredient.texture; }
    }

}
