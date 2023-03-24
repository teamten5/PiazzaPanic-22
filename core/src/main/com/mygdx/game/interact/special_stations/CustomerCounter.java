package com.mygdx.game.interact.special_stations;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Ingredient;
import com.mygdx.game.customer.Customer;
import com.mygdx.game.customer.CustomerEngine;

/**
 * @author Thomas McCarthy
 *
 * An extension of the Counter class. This one can be assigned a customer.
 * This class is accessed by both Interact and Customer engines.
 */
public class CustomerCounter extends Counter {

    Ingredient requiredIngredient;
    Customer customer;
    Texture blank = new Texture("_blank.png");


    //==========================================================\\
    //                      CONSTRUCTOR                         \\
    //==========================================================\\

    public CustomerCounter(float xPos, float yPos) {
        super(xPos, yPos);
        requiredIngredient = null;
        customer = null;

        CustomerEngine.addCustomerCounter(this);
    }


    //==========================================================\\
    //                      INTERACTION                         \\
    //==========================================================\\

    @Override
    public void handleInteraction()
    {
        super.handleInteraction();

        if(customer != null && storedIngredient == requiredIngredient)
        {
            customer.completeOrder();
            storedIngredient = null;
            customer = null;
        }
    }


    //==========================================================\\
    //                    GETTERS & SETTERS                     \\
    //==========================================================\\

    @Override
    public Texture getIngredientTexture()
    {
        if(customer == null)
        {
            return blank;
        } else {
            return requiredIngredient.texture;
        }
    }

    public void placeOrder(Customer customer, Ingredient requiredIngredient)
    {
        this.customer = customer;
        this.requiredIngredient = requiredIngredient;
    }
}
