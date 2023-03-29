package com.mygdx.game.customer;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Ingredient;
import com.mygdx.game.interact.InteractableBase;
import com.mygdx.game.interact.special_stations.CustomerCounter;

import java.util.LinkedList;

/**
 *
 * @author Thomas McCarthy
 *
 * The PlayerEngine class creates and renders the games' three chefs. It also handles
 * chef switching and detects interaction input (although the handling of interactions
 * is then done by the InteractEngine)
 *
 */
public final class CustomerEngine {

    static Ingredient[] recipes;
    static LinkedList<CustomerCounter> customerCounters;
    static LinkedList<Customer> customers;
    static int maxCustomers;
    static float minTimeGap;
    static float maxTimeGap;
    static float timer;


    // This will need to be changed when the customer count can be altered. For endless mode this can be set to -1
    static int numberOfCustomers;

    static Texture customerTexture;


    //==========================================================\\
    //                      INITIALISER                         \\
    //==========================================================\\

    public static void initialise()
    {

        // Recipes is the array of items a customer can order
        recipes = new Ingredient[] {
              InteractableBase.ingredientHashMap.get("burger"),
              InteractableBase.ingredientHashMap.get("salad")
        };

        customerTexture = new Texture("textures/customer.png");
        customerCounters = new LinkedList<>();
        customers = new LinkedList<>();
        minTimeGap = 2f;
        maxTimeGap = 10f;
        timer = 0f;

        maxCustomers = 1;
        numberOfCustomers = 5;
    }


    //==========================================================\\
    //                         UPDATE                           \\
    //==========================================================\\

    public static void update(float delta)
    {
        // Render the customers
        for(Customer c : customers)
        {
            c.update();
        }

        if(timer <= 0 && customers.size() < maxCustomers && numberOfCustomers != 0)
        {
            int random = (int)(Math.random() * recipes.length);
            Customer customer = new Customer(customerCounters.get(0), recipes[random]);
            customers.add(customer);
            timer = minTimeGap + ((float)Math.random() * (maxTimeGap - minTimeGap));
        }

        timer -= delta;
    }

    public static void render(SpriteBatch batch) {
        for(Customer c : customers)
        {
            batch.draw(customerTexture, c.getXPos(), c.getYPos(), 1, 1);
        }
    }


    //==========================================================\\
    //                    GETTERS & SETTERS                     \\
    //==========================================================\\

    public static void addCustomerCounter(CustomerCounter counter)
    {
        customerCounters.add(counter);
    }


    public static void removeCustomer(Customer customer)
    {
        customers.remove(customer);
        numberOfCustomers--;
    }

    public static int getCustomersRemaining()
    {
        return numberOfCustomers;
    }

}