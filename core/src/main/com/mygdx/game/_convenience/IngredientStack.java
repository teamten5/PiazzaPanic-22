package com.mygdx.game._convenience;

import com.mygdx.game.Ingredient;

import java.util.LinkedList;

/**
 * @author Thomas McCarthy
 *
 * An implementation of an IngredientName stack.
 */


public class IngredientStack {
    private LinkedList<Ingredient> stack;
    private int maxSize;


    //==========================================================\\
    //                     CONSTRUCTORS                         \\
    //==========================================================\\

    // Implementation of an unlimited size stack
    public IngredientStack()
    {
        stack = new LinkedList<Ingredient>();
        maxSize = -1;
    }

    // Implementation of a stack with a maximum size
    public IngredientStack(int maxSize)
    {
        stack = new LinkedList<Ingredient>();
        maxSize = maxSize;
    }


    //==========================================================\\
    //                    STACK FUNCTIONS                       \\
    //==========================================================\\

    // Adds a new ingredient to the top of the stack
    public boolean push(Ingredient ingredient)
    {
        if(maxSize == -1 || stack.size() < maxSize)
        {
            stack.add(ingredient);
            return true;
        }
        return false;
    }

    // Returns the top element of the stack, but doesn't remove it
    public Ingredient peek()
    {
        if(stack.size() > 0)
        {
            return stack.get(stack.size() - 1);
        }
        return null;
    }

    // Removes and returns the top element of the stack
    public Ingredient pop()
    {
        Ingredient ingredient = peek();
        if(ingredient != null)
        {
            stack.removeLast();
        }
        System.out.println("POPPED " + ingredient + " FROM CHEF STACK");
        return ingredient;
    }

    // Returns the element at a certain depth in the stack
    public Ingredient peekAtDepth(int depth)
    {
        try
        {
            Ingredient ingredient = stack.get(stack.size() - depth);
            return ingredient;
        }
        catch(Exception e)
        {
            return null;
        }
    }

}
