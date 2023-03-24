package com.mygdx.game.interact.special_stations.assembly_stations;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.player.PlayerEngine;


/**
 * @author Thomas McCarthy
 *
 * A type of special station used for assembling the completed foods.
 */
public class AssemblyStation extends InteractableBase {

    protected Ingredient[] recipe;
    protected Ingredient outputIngredient;
    protected int assemblyIndex;
    boolean isAssembled;


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public AssemblyStation(float xPos, float yPos, String texture) {
        super(xPos, yPos, texture);

        this.outputIngredient = null;
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
        Ingredient chefIngredient = PlayerEngine.getActiveChef().getIngredientStack().peek();

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
    public Texture getIngredientTexture()
    {
        if(isAssembled)
        {
            return outputIngredient.texture;
        }
        else {
            return recipe[assemblyIndex].texture;
        }
    }

    public void setRecipe(Ingredient[] recipe, Ingredient outputIngredient)
    {
        this.recipe = recipe;
        this.outputIngredient = outputIngredient;
    }

}
