package com.mygdx.game.interact.special_stations.assembly_stations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.ingredient.IngredientName;
import com.mygdx.game.ingredient.IngredientTextures;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;


/**
 * @author Thomas McCarthy
 *
 * A type of special station used for assembling the completed foods.
 */
public class AssemblyStation extends InteractableBase {

    protected IngredientName[] recipe;
    protected IngredientName outputIngredient;
    protected int assemblyIndex;
    boolean isAssembled;


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public AssemblyStation(float xPos, float yPos, String texture) {
        super(xPos, yPos, texture);

        this.outputIngredient = IngredientName.NULL_INGREDIENT;
        this.recipe = null;

        assemblyIndex = 0;
        isAssembled = false;
    }


    //==========================================================\\
    //                      INTERACTION                         \\
    //==========================================================\\

    @Override
    public void handleInteraction()
    {
        IngredientName chefIngredient = PlayerEngine.getActiveChef().getIngredientStack().peek();

        if(isAssembled)
        {
            PlayerEngine.getActiveChef().getIngredientStack().push(outputIngredient);
            isAssembled = false;
            assemblyIndex = 0;
        }
        else
        {
            if(recipe[assemblyIndex] == chefIngredient)
            {
                assemblyIndex++;
                PlayerEngine.getActiveChef().getIngredientStack().pop();

                if(assemblyIndex == recipe.length)
                {
                    isAssembled = true;
                }
            }
        }
    }


    //==========================================================\\
    //                    GETTERS & SETTERS                     \\
    //==========================================================\\

    @Override
    public Sprite getIngredientSprite()
    {
        if(isAssembled)
        {
            return new Sprite(IngredientTextures.getTexture(outputIngredient));
        }
        else {
            Sprite sprite = new Sprite(IngredientTextures.getTexture(recipe[assemblyIndex]));
            sprite.setColor(60f, 60f, 60f, 0.25f);
            sprite.setScale(0.8f, 0.8f);
            return sprite;
        }
    }

    public void setRecipe(IngredientName[] recipe, IngredientName outputIngredient)
    {
        this.recipe = recipe;
        this.outputIngredient = outputIngredient;
    }

}
