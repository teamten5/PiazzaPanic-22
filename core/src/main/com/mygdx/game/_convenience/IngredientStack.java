package com.mygdx.game._convenience;

import com.mygdx.game.ingredient.IngredientName;

import java.util.LinkedList;

/**
 * @author Thomas McCarthy
 *
 * An implementation of an IngredientName stack.
 */


public class IngredientStack {
    private LinkedList<IngredientName> stack;
    private int maxSize;


    //==========================================================\\
    //                     CONSTRUCTORS                         \\
    //==========================================================\\

    // Implementation of an unlimited size stack
    public IngredientStack()
    {
        stack = new LinkedList<IngredientName>();
        maxSize = -1;
    }

    // Implementation of a stack with a maximum size
    public IngredientStack(int maxSize)
    {
        stack = new LinkedList<IngredientName>();
        maxSize = maxSize;
    }


    //==========================================================\\
    //                    STACK FUNCTIONS                       \\
    //==========================================================\\

    // Adds a new ingredient to the top of the stack
    public boolean push(IngredientName ingredient)
    {
        if(maxSize == -1 || stack.size() < maxSize)
        {
            stack.add(ingredient);
            return true;
        }
        return false;
    }

    // Returns the top element of the stack, but doesn't remove it
    public IngredientName peek()
    {
        if(stack.size() > 0)
        {
            return stack.get(stack.size() - 1);
        }
        return IngredientName.NULL_INGREDIENT;
    }

    // Removes and returns the top element of the stack
    public IngredientName pop()
    {
        IngredientName ingredient = peek();
        if(ingredient != IngredientName.NULL_INGREDIENT)
        {
            stack.removeLast();
        }
        System.out.println("POPPED " + ingredient + " FROM CHEF STACK");
        return ingredient;
    }

    // Returns the element at a certain depth in the stack
    public IngredientName peekAtDepth(int depth)
    {
        try
        {
            IngredientName ingredient = stack.get(stack.size() - depth);
            return ingredient;
        }
        catch(Exception e)
        {
            return IngredientName.NULL_INGREDIENT;
        }
    }

}
