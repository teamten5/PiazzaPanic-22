package com.mygdx.game.interact;

import com.mygdx.game.Ingredient;
import com.mygdx.game.actors.Spot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class InteractableInLevel {
    public float xPos;
    public float yPos;
    public InteractableType type;
    protected ArrayList<Combination> combinations;
    protected HashMap<Ingredient, Action> actions;
    public List<Spot> attachedSpots = new ArrayList<>();

    public InteractableInLevel(InteractableType type, float xPos, float yPos,
          ArrayList<Combination> combinations, HashMap<Ingredient, Action> actions) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
        if (combinations == null) {
            this.combinations = new ArrayList<>(0);
        } else {
            this.combinations = combinations;
        }
        if (actions == null) {
            this.actions = new HashMap<>(0);
        } else {
            this.actions = actions;
        }
    }
    public Interactable initialise() {
        return new Interactable(this);
    }
}
