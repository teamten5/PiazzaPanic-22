package com.mygdx.game.interact;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Ingredient;
import java.util.ArrayList;
import java.util.HashMap;

public class InteractableInLevel {
    protected float xPos;
    protected float yPos;
    protected InteractableType type;
    protected ArrayList<Combination> combinations;
    protected HashMap<Ingredient, Action> actions;

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
