package com.mygdx.game.interact.special_stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.ingredient.IngredientTextures;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;


/**
 * @author Thomas McCarthy
 *
 * A counter is used for placing ingredients. It has no processes.
 */
public class Counter extends InteractableBase {

    protected IngredientName storedIngredient;


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
            IngredientName ingredient = PlayerEngine.getActiveChef().getIngredientStack().peek();
            if(!ingredient.equals(IngredientName.NULL_INGREDIENT))
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
    public Sprite getIngredientSprite()
    {
        if(storedIngredient == null)    { return new Sprite(super.indicatorArrow); }
        else                            { return new Sprite(IngredientTextures.getTexture(storedIngredient)); }
    }

}
